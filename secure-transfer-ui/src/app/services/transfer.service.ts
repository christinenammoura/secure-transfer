import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class TransferService {
  private apiUrl = `${environment.apiUrl}/transfers`;

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
