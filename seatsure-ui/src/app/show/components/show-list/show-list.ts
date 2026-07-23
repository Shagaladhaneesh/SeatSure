import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RouterLink } from '@angular/router';
import { Show } from '../../models/show';
import { ShowService } from '../../services/show.service';

@Component({
  selector: 'app-show-list',
  standalone: true,
  imports: [
    CommonModule,RouterLink
  ],
  templateUrl: './show-list.html',
  styleUrl: './show-list.css'
})
export class ShowListComponent implements OnInit {

  shows: Show[] = [];

  private showService = inject(ShowService);
private cdr = inject(ChangeDetectorRef);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {

      const movieId =
      Number(this.route.snapshot.paramMap.get('movieId'));

      this.loadShows(movieId);

  }

  loadShows(movieId:number){

      this.showService.getShowsByMovie(movieId)
      .subscribe({

          next:(response)=>{

              this.shows=response;
              this.cdr.detectChanges();

          },

          error:(err)=>{

              console.error(err);

          }

      });

  }

}
