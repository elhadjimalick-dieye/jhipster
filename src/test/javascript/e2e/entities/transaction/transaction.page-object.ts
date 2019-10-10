import { element, by, ElementFinder } from 'protractor';

export class TransactionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-transaction div table .btn-danger'));
  title = element.all(by.css('jhi-transaction div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class TransactionUpdatePage {
  pageTitle = element(by.id('jhi-transaction-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  fraisInput = element(by.id('field_frais'));
  montantInput = element(by.id('field_montant'));
  commissionEtatInput = element(by.id('field_commissionEtat'));
  commissionSystemeInput = element(by.id('field_commissionSysteme'));
  commissionReceveurInput = element(by.id('field_commissionReceveur'));
  commissionEnvoyeurInput = element(by.id('field_commissionEnvoyeur'));
  clientEnvoyeurSelect = element(by.id('field_clientEnvoyeur'));
  clientReceveurSelect = element(by.id('field_clientReceveur'));
  userEnvoyerSelect = element(by.id('field_userEnvoyer'));
  userReceveurSelect = element(by.id('field_userReceveur'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFraisInput(frais) {
    await this.fraisInput.sendKeys(frais);
  }

  async getFraisInput() {
    return await this.fraisInput.getAttribute('value');
  }

  async setMontantInput(montant) {
    await this.montantInput.sendKeys(montant);
  }

  async getMontantInput() {
    return await this.montantInput.getAttribute('value');
  }

  async setCommissionEtatInput(commissionEtat) {
    await this.commissionEtatInput.sendKeys(commissionEtat);
  }

  async getCommissionEtatInput() {
    return await this.commissionEtatInput.getAttribute('value');
  }

  async setCommissionSystemeInput(commissionSysteme) {
    await this.commissionSystemeInput.sendKeys(commissionSysteme);
  }

  async getCommissionSystemeInput() {
    return await this.commissionSystemeInput.getAttribute('value');
  }

  async setCommissionReceveurInput(commissionReceveur) {
    await this.commissionReceveurInput.sendKeys(commissionReceveur);
  }

  async getCommissionReceveurInput() {
    return await this.commissionReceveurInput.getAttribute('value');
  }

  async setCommissionEnvoyeurInput(commissionEnvoyeur) {
    await this.commissionEnvoyeurInput.sendKeys(commissionEnvoyeur);
  }

  async getCommissionEnvoyeurInput() {
    return await this.commissionEnvoyeurInput.getAttribute('value');
  }

  async clientEnvoyeurSelectLastOption(timeout?: number) {
    await this.clientEnvoyeurSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async clientEnvoyeurSelectOption(option) {
    await this.clientEnvoyeurSelect.sendKeys(option);
  }

  getClientEnvoyeurSelect(): ElementFinder {
    return this.clientEnvoyeurSelect;
  }

  async getClientEnvoyeurSelectedOption() {
    return await this.clientEnvoyeurSelect.element(by.css('option:checked')).getText();
  }

  async clientReceveurSelectLastOption(timeout?: number) {
    await this.clientReceveurSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async clientReceveurSelectOption(option) {
    await this.clientReceveurSelect.sendKeys(option);
  }

  getClientReceveurSelect(): ElementFinder {
    return this.clientReceveurSelect;
  }

  async getClientReceveurSelectedOption() {
    return await this.clientReceveurSelect.element(by.css('option:checked')).getText();
  }

  async userEnvoyerSelectLastOption(timeout?: number) {
    await this.userEnvoyerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userEnvoyerSelectOption(option) {
    await this.userEnvoyerSelect.sendKeys(option);
  }

  getUserEnvoyerSelect(): ElementFinder {
    return this.userEnvoyerSelect;
  }

  async getUserEnvoyerSelectedOption() {
    return await this.userEnvoyerSelect.element(by.css('option:checked')).getText();
  }

  async userReceveurSelectLastOption(timeout?: number) {
    await this.userReceveurSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userReceveurSelectOption(option) {
    await this.userReceveurSelect.sendKeys(option);
  }

  getUserReceveurSelect(): ElementFinder {
    return this.userReceveurSelect;
  }

  async getUserReceveurSelectedOption() {
    return await this.userReceveurSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class TransactionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-transaction-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-transaction'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
