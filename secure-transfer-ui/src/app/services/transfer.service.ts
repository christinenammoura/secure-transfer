import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TransferService {
  private apiUrl = 'http://localhost:8080/api/transfers';

  constructor(private http: HttpClient) {}

  transfer(recipientAccount: string, amount: number) {
    const token = localStorage.getItem('token');
    return this.http.post(
      this.apiUrl,
      { recipientAccount, amount },
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
  }
}
