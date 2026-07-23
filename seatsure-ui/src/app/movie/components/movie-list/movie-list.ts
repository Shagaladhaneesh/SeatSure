import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';
import { Movie } from '../../models/movie';
import { MovieService } from '../../services/movie.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-movie-list',
  standalone: true,
 imports:[
 CommonModule,
 RouterLink
 ],
  templateUrl: './movie-list.html',
  styleUrl: './movie-list.css'
})
export class MovieListComponent implements OnInit {

  movies: Movie[] = [];
private cdr = inject(ChangeDetectorRef);
  private movieService = inject(MovieService);

  ngOnInit(): void {

    console.log("Movie Component Loaded");

    this.loadMovies();

  }

 loadMovies() {

   this.movieService.getMovies().subscribe({

     next: (response) => {

       console.log("Response:", response);

     this.movies = response;

     this.cdr.detectChanges();

       console.log("Movies:", this.movies);
       console.log("Length:", this.movies.length);

     },

     error: (err) => {

       console.error(err);

     }

   });

 }
}
