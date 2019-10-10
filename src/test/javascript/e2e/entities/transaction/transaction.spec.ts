// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { TransactionComponentsPage, TransactionDeleteDialog, TransactionUpdatePage } from './transaction.page-object';

const expect = chai.expect;

describe('Transaction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let transactionUpdatePage: TransactionUpdatePage;
  let transactionComponentsPage: TransactionComponentsPage;
  let transactionDeleteDialog: TransactionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Transactions', async () => {
    await navBarPage.goToEntity('transaction');
    transactionComponentsPage = new TransactionComponentsPage();
    await browser.wait(ec.visibilityOf(transactionComponentsPage.title), 5000);
    expect(await transactionComponentsPage.getTitle()).to.eq('projetJhipsterApp.transaction.home.title');
  });

  it('should load create Transaction page', async () => {
    await transactionComponentsPage.clickOnCreateButton();
    transactionUpdatePage = new TransactionUpdatePage();
    expect(await transactionUpdatePage.getPageTitle()).to.eq('projetJhipsterApp.transaction.home.createOrEditLabel');
    await transactionUpdatePage.cancel();
  });

  it('should create and save Transactions', async () => {
    const nbButtonsBeforeCreate = await transactionComponentsPage.countDeleteButtons();

    await transactionComponentsPage.clickOnCreateButton();
    await promise.all([
      transactionUpdatePage.setFraisInput('5'),
      transactionUpdatePage.setMontantInput('5'),
      transactionUpdatePage.setCommissionEtatInput('5'),
      transactionUpdatePage.setCommissionSystemeInput('5'),
      transactionUpdatePage.setCommissionReceveurInput('5'),
      transactionUpdatePage.setCommissionEnvoyeurInput('5'),
      transactionUpdatePage.clientEnvoyeurSelectLastOption(),
      transactionUpdatePage.clientReceveurSelectLastOption(),
      transactionUpdatePage.userEnvoyerSelectLastOption(),
      transactionUpdatePage.userReceveurSelectLastOption()
    ]);
    expect(await transactionUpdatePage.getFraisInput()).to.eq('5', 'Expected frais value to be equals to 5');
    expect(await transactionUpdatePage.getMontantInput()).to.eq('5', 'Expected montant value to be equals to 5');
    expect(await transactionUpdatePage.getCommissionEtatInput()).to.eq('5', 'Expected commissionEtat value to be equals to 5');
    expect(await transactionUpdatePage.getCommissionSystemeInput()).to.eq('5', 'Expected commissionSysteme value to be equals to 5');
    expect(await transactionUpdatePage.getCommissionReceveurInput()).to.eq('5', 'Expected commissionReceveur value to be equals to 5');
    expect(await transactionUpdatePage.getCommissionEnvoyeurInput()).to.eq('5', 'Expected commissionEnvoyeur value to be equals to 5');
    await transactionUpdatePage.save();
    expect(await transactionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await transactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Transaction', async () => {
    const nbButtonsBeforeDelete = await transactionComponentsPage.countDeleteButtons();
    await transactionComponentsPage.clickOnLastDeleteButton();

    transactionDeleteDialog = new TransactionDeleteDialog();
    expect(await transactionDeleteDialog.getDialogTitle()).to.eq('projetJhipsterApp.transaction.delete.question');
    await transactionDeleteDialog.clickOnConfirmButton();

    expect(await transactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
