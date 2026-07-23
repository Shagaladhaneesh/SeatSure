import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';
import { ShowSeat } from '../../models/show-seat';
import { ShowSeatService } from '../../services/show-seat.service';
import { Router } from '@angular/router';
import { BookingService } from '../../../booking/services/booking.service';
@Component({
  selector: 'app-seat-selection',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './seat-selection.html',
  styleUrl: './seat-selection.css'
})
export class SeatSelectionComponent implements OnInit {
  private bookingService = inject(BookingService);

  private router = inject(Router);

  seats: ShowSeat[] = [];

  selectedSeats: ShowSeat[] = [];

  private route = inject(ActivatedRoute);
  private cdr = inject(ChangeDetectorRef);
  private showSeatService = inject(ShowSeatService);

  ngOnInit(): void {

    const showId =
      Number(this.route.snapshot.paramMap.get('showId'));

    this.loadSeats(showId);

  }

  loadSeats(showId: number) {

    this.showSeatService.getSeats(showId)
      .subscribe({

        next: (response) => {

          this.seats = response;
          this.cdr.detectChanges();



        },

        error: (err) => {

          console.error(err);

        }

      });

  }
toggleSeat(seat: ShowSeat) {

  if (seat.status !== 'AVAILABLE') {
    return;
  }

  const index =
    this.selectedSeats.findIndex(
      s => s.showSeatId === seat.showSeatId
    );

  if (index >= 0) {

    this.selectedSeats.splice(index, 1);

  } else {

    if (this.selectedSeats.length >= 6) {

      alert("Maximum 6 seats allowed");

      return;

    }

    this.selectedSeats.push(seat);

  }

}
isSelected(seat: ShowSeat): boolean {

  return this.selectedSeats.some(

      s => s.showSeatId === seat.showSeatId

  );

}

bookSeats(){

    if(this.selectedSeats.length===0){

        alert("Please select at least one seat");

        return;

    }

    const request={

        showSeatIds:this.selectedSeats.map(

            seat=>seat.showSeatId

        )

    };

    this.bookingService.bookSeats(request)
        .subscribe({

            next:(response)=>{

                alert("Booking Successful");

                this.router.navigate(
                    ['/booking-history']
                );

            },

            error:(err)=>{

                console.error(err);

                alert("Booking Failed");

            }

        });

}
}
