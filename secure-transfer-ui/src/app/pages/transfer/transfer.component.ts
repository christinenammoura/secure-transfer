import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TransferService } from '../../services/transfer.service';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css'],
})
export class TransferComponent implements OnInit {
  username = '';
  balance = 0;
  recipientAccount = '';
  amount: number | null = null;
  successMessage = '';
  errorMessage = '';

  constructor(
    private transferService: TransferService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.authService.getUserDetails().subscribe({
      next: (data: any) => {
        this.username = data.username;
        this.balance = data.balance;
      },
      error: () => this.router.navigate(['/login']),
    });
  }

  onSubmit() {
    this.successMessage = '';
    this.errorMessage = '';

    if (!this.recipientAccount || !this.amount || this.amount <= 0) {
      this.errorMessage = 'Please enter valid details.';
      return;
    }

    this.transferService.transfer(this.recipientAccount, this.amount).subscribe({
      next: (res: any) => {
        if (res.message) {
          //  success case
          this.successMessage = res.message;
          this.errorMessage = '';
          this.balance -= this.amount!;
        } else {
          //  backend returned a structured error
          this.errorMessage = res.error || 'Transfer failed.';
        }
      },
      error: (err) => {
        //  backend threw an error (e.g., self-transfer, insufficient funds)
        if (err.error && err.error.error) {
          this.errorMessage = err.error.error; // backend “error” field
        } else {
          this.errorMessage = 'Transfer failed.';
        }
        this.successMessage = '';
      },
    });
  }
}
