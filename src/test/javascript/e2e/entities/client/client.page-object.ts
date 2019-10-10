import { element, by, ElementFinder } from 'protractor';

export class ClientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-client div table .btn-danger'));
  title = element.all(by.css('jhi-client div h2#page-heading span')).first();

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

export class ClientUpdatePage {
  pageTitle = element(by.id('jhi-client-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  prenomInput = element(by.id('field_prenom'));
  nomInput = element(by.id('field_nom'));
  telephoneInput = element(by.id('field_telephone'));
  cniInput = element(by.id('field_cni'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPrenomInput(prenom) {
    await this.prenomInput.sendKeys(prenom);
  }

  async getPrenomInput() {
    return await this.prenomInput.getAttribute('value');
  }

  async setNomInput(nom) {
    await this.nomInput.sendKeys(nom);
  }

  async getNomInput() {
    return await this.nomInput.getAttribute('value');
  }

  async setTelephoneInput(telephone) {
    await this.telephoneInput.sendKeys(telephone);
  }

  async getTelephoneInput() {
    return await this.telephoneInput.getAttribute('value');
  }

  async setCniInput(cni) {
    await this.cniInput.sendKeys(cni);
  }

  async getCniInput() {
    return await this.cniInput.getAttribute('value');
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

export class ClientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-client-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-client'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
