
import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookingService } from '../../services/booking.service';
import { BookingSummary } from '../../models/booking-summary';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-booking-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './booking-history.html',
  styleUrl: './booking-history.css'
})
export class BookingHistoryComponent implements OnInit {

  bookings: BookingSummary[] = [];

  private bookingService = inject(BookingService);
    private cdr = inject(ChangeDetectorRef);


  ngOnInit(): void {

    this.loadBookings();


  }
cancelBooking(id:number){

    this.bookingService.cancelBooking(id)
        .subscribe({

            next:()=>{

                alert("Booking Cancelled");

                this.loadBookings();


            },

            error:(err)=>{

                console.error(err);

            }

        });

}
  loadBookings() {

    this.bookingService.getMyBookings()
      .subscribe({

        next: (response) => {

          this.bookings = response;
           this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

}
