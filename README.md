# Skema Test

## Unit test

### Test MovieRepository

* Memuat Movies 
  * Memanipulasi data ketika pemanggilan data movies di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan movies tidak null
  * Memastikan jumlah data movies sesuai dengan yang diharapkan

* Memuat TV Shows
  * Memanipulasi data ketika pemanggilan data tv shows di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan tv shows tidak null 
  * Memastikan jumlah data tv shows sesuai dengan yang diharapkan
    
* Memuat Favorites Movies
  * Memanipulasi data ketika pemanggilan data favorites di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan favorites movies tidak null 
  * Memastikan favorites movies kosong

* Memuat Favorites Tv Shows 
  * Memanipulasi data ketika pemanggilan data favorites di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan favorites tv shows tidak null 
  * Memastikan favorites tv shows kosong
  
* Memuat Detail Movie
  * Memanipulasi data ketika pemanggilan data movie di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan movie tidak null

* Memuat Detail TV Show
  * Memanipulasi data ketika pemanggilan data tv show di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan tv show tidak null
  
* Mencari Movies
  * Memanipulasi data ketika pemanggilan pencarian movies di kelas repository
  * Memastikan kelas cari movies di repository terpanggil
  * Memastikan hasil pencarian tidak null
  * Memastikan hasil pencarian tidak kosong
  
* Mencari TV Shows
  * Memanipulasi data ketika pemanggilan pencarian tv shows di kelas repository
  * Memastikan kelas cari tv shows di repository terpanggil
  * Memastikan hasil pencarian tidak null
  * Memastikan hasil pencarian tidak kosong
  
* Mencari Favorite Movies
  * Memanipulasi data ketika pemanggilan pencarian favorite movies di kelas repository
  * Memastikan kelas cari favorite movies di repository terpanggil
  * Memastikan hasil pencarian tidak null
  * Memastikan hasil pencarian tidak kosong
  
* Mencari Favorite TV Shows
  * Memanipulasi data ketika pemanggilan pencarian favorite tv shows di kelas repository
  * Memastikan kelas cari favorite tv shows di repository terpanggil
  * Memastikan hasil pencarian tidak null
  * Memastikan hasil pencarian tidak kosong
  
### Test ViewModel

#### HomeViewModelTest:
* Memuat Movies 
  * Memanipulasi data ketika pemanggilan data movies di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan movies tidak null
  * Memastikan jumlah data movies sesuai dengan yang diharapkan

* Memuat TV Shows
  * Memanipulasi data ketika pemanggilan data tv shows di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan tv shows tidak null 
  * Memastikan jumlah data tv shows sesuai dengan yang diharapkan
    
* Memuat Favorite Movies
  * Memanipulasi data ketika pemanggilan data favorite movies di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan favorites movies tidak null 
  * Memastikan favorites movies kosong

* Memuat Favorite TV Show
  * Memanipulasi data ketika pemanggilan data favorite tv shows di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan favorites tv shows tidak null 
  * Memastikan favorites tv shows kosong

* Mencari Movies
  * Memanipulasi data ketika pemanggilan method pencarian movies di kelas repository
  * Memastikan metode pencarian movies di kelas repository terpanggil
  * Memastikan data movies tidak null 
  * Memastikan data movies kosong

* Mencari TV Show
  * Memanipulasi data ketika pemanggilan method pencarian tv shows di kelas repository
  * Memastikan metode pencarian tv shows di kelas repository terpanggil
  * Memastikan data tv shows tidak null 
  * Memastikan data tv shows kosong

* Mencari Favorite Movies
  * Memanipulasi data ketika pemanggilan method pencarian favorite movies di kelas repository
  * Memastikan metode pencarian favorite movies di kelas repository terpanggil
  * Memastikan data favorite movies tidak null 
  * Memastikan data favorite movies kosong
  
* Mencari Favorite TV Show
  * Memanipulasi data ketika pemanggilan method pencarian favorite tv shows di kelas repository
  * Memastikan metode pencarian favorite tv shows di kelas repository terpanggil
  * Memastikan data favorite tv shows tidak null 
  * Memastikan data favorite tv shows kosong

* Menyimpan Favorite
  * Memastikan metode simpan di kelas repository terpanggil
    
* Menghapus Favorite
  * Memastikan metode delete di kelas repository terpanggil
    
#### DetailMovieViewModelTest:
* Memuat Movie
  * Memanipulasi data ketika pemanggilan data movie di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan movie tidak null 
  * Memastikan movie sesuai dengan yang diharapkan
    
    
#### DetailTvShowViewModelTest:
* Memuat Tv Show
  * Memanipulasi data ketika pemanggilan data tv show di kelas repository
  * Memastikan metode di kelas repository terpanggil
  * Memastikan tv show tidak null 
  * Memastikan tv show sesuai dengan yang diharapkan
    

## Instrumentation Test

### HomeActivityTest:
Catatan: Sebelum melakukan instrumentation test pastikan tab favorite movies dan tab
         favorite tv shows masih kosong.

* Menampilkan halaman Movies
  * Memastikan halaman Movies tampil dengan data yang diharapkan
  
* Menampilkan detail Movie
  * Klik item movie
  * Memastikan detail movie tampil dengan data yang dipilih

* Menampilkan halaman TV Shows
  * Memastikan halaman TV Shows tampil dengan data yang diharapakn

* Menampilkan detail Tv Show
  * Klik item tv show
  * Memastikan detail Tv Show tampil dengan data yang dipilih
  
* Menampilkan halaman Favorite
  * Memastikan halaman Favorite tampil dengan data yang diharapkan

* Menampilkan detail Favorite
  * Klik item favorite
  * Memastikan detail Favorite menampilkan data yang dipilih

* Menambahkan movie ke Favorites dari list
  * Klik tombol favorite
  * Memastik item muncul di favorites

* Menambahkan tv show ke Favorites dari list
  * Klik tombol favorite
  * Memastik item muncul di favorites
  
* Menambahkan movie ke Favorite dari halaman detail
  * Klik tombol favorite
  * Memastik item muncul di favorites

* Menambahkan tv show ke Favorite dari halaman detail
  * Klik tombol favorite
  * Memastik item muncul di favorites
  
* Menghapus item di favorites movies
  * Klik tombol trash
  * Memastikan item terhapus dari favorite movies
  
* Menghapus item di favorites tv shows
  * Klik tombol trash
  * Memastikan item terhapus dari favorite tv shows
  
* Melakukan pencarian movies
  * Klik tab movies
  * Klik pencarian
  * type keyword
  * Memastikan data hasil ditampilkan
  
* Melakukan pencarian tv shows
  * Klik tab tv show
  * Klik pencarian
  * type keyword
  * Memastikan data hasil ditampilkan

* Melakukan pencarian favorite movies
  * Klik tab my favorite
  * Klik tab my movies
  * Klik pencarian
  * type keyword
  * Memastikan snackbar muncul karena data masih kosong
  
* Melakukan pencarian favorite tv shows
  * Klik tab my favorite
  * Klik tab my tv show
  * Klik pencarian
  * type keyword
  * Memastikan snackbar muncul karena data masih kosong