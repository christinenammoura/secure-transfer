import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { TransferComponent } from './pages/transfer/transfer.component';
import { AuthGuard } from './services/auth.guard';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'transfer', component: TransferComponent, canActivate: [AuthGuard] },
  { path: 'transfer', component: TransferComponent },
  { path: '', redirectTo: '/transfer', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' },
];
