import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'transaction',
        loadChildren: () => import('./transaction/transaction.module').then(m => m.ProjetJhipsterTransactionModule)
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.ProjetJhipsterClientModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ProjetJhipsterEntityModule {}
