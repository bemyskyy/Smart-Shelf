import { Routes } from '@angular/router';
import { AuthComponent } from './features/auth/auth.component';
import { LayoutComponent } from './shared/components/layout/layout.component';
import { CatalogComponent } from './features/catalog/catalog.component';
import { MyItemsComponent } from './features/my-items/my-items.component';
import { RequestsComponent } from './features/requests/requests.component';
import { NotFoundComponent } from './features/not-found/not-found.component';
import { authGuard } from './core/guards/auth.guard';
import { guestGuard } from './core/guards/guest.guard';

export const routes: Routes = [
  {
    path: 'login',
    component: AuthComponent,
    canActivate: [guestGuard]
  },

  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'catalog', component: CatalogComponent },
      { path: 'my-items', component: MyItemsComponent },
      { path: 'requests', component: RequestsComponent },
      { path: '', redirectTo: 'catalog', pathMatch: 'full' }
    ]
  },

  { path: '**', component: NotFoundComponent }
];
