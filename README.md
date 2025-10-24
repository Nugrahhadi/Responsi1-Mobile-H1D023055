# Responsi 1 Mobile - H1D023055
# Aplikasi Informasi Klub Sepak Bola: Borussia Mönchengladbach

## Nama : Muhammad Nugrahhadi Al Khawarizmi
## NIM  : H1D023055
## Shift Sekarang: A
## Shift Asal : G

---

# Video Demo:


## 📊 Alur Data

### API Setup
- **Base URL**: `https://api.football-data.org/v4/`
- **Endpoint**: `GET /teams/{id}`
- **Team ID**: `18` (Borussia Mönchengladbach)
- **Header**: `X-Auth-Token: 90a829184dc64ee59e72a3f2244d90ba`

### Alur Pengambilan Data

```
API Request (football-data.org)
    ↓
Retrofit + Gson (parse JSON)
    ↓
Data Model (ApiResponse, Player, Coach)
    ↓
TeamRepository (fetch data dari API)
    ↓
Activity dengan Coroutines (async data loading)
    ↓
Update UI dengan data dari API
```

---

## 🏗️ Struktur Activities

### 1. **MainActivity**
- Menampilkan foto stadion Borussia-Park
- Menampilkan logo klub
- Menampilkan nama klub: "Borussia Mönchengladbach"
- Menampilkan deskripsi klub
- Menampilkan informasi dari API:
  - Founded (tahun didirikan)
  - Venue (nama stadion)
  - Address (alamat klub)
- Tiga tombol navigasi:
  - **History** → ke HistoryActivity
  - **Head Coach** → ke CoachActivity
  - **Players** → ke PlayersActivity

### 2. **HistoryActivity**
- Menampilkan logo Borussia Mönchengladbach
- Menampilkan judul "Club History"
- Menampilkan sejarah klub lengkap:
  - Pendirian tahun 1900
  - 5 gelar Bundesliga (1970, 1971, 1975, 1976, 1977)
  - 3 gelar DFB-Pokal
  - Masa kejayaan di era 1970-an
  - Julukan "Die Fohlen" (The Foals)
- **Background card**: #202020 (dark theme)
- **Text color**: White

### 3. **CoachActivity**
- Memanggil API untuk mendapatkan data pelatih
- Menampilkan foto pelatih (`coach_polanski.jpg`)
- Menampilkan informasi pelatih dari API:
  - **Nama lengkap**
  - **Tanggal lahir** (Date of Birth)
  - **Kebangsaan** (Nationality)
  - **Kontrak** (Contract period)
- **Background card**: #202020 (dark theme)
- **Text color**: White
- Progress bar saat loading
- Error handling jika API gagal

### 4. **PlayersActivity**
- Memanggil API untuk mendapatkan daftar pemain
- Menampilkan semua pemain dalam satu RecyclerView
- **Sorting otomatis** berdasarkan posisi:
  1. **Goalkeeper** (GK) → Card Kuning (`#FFEB3B`)
  2. **Defence** (DEF) → Card Biru (`#2196F3`)
  3. **Midfield** (MID) → Card Hijau (`#4CAF50`)
  4. **Offence** (FW) → Card Merah (`#F44336`)
- Setiap card menampilkan:
  - **Nama pemain**
  - **Negara asal**
- **Detail pemain** tampil di **Bottom Sheet** saat card diklik:
  - Nama lengkap
  - Position
  - Nationality
  - Date of Birth
- **Bottom Sheet styling**:
  - Muncul dari bawah
  - Rounded top corners (15dp)
  - Background: #202020
  - Text: White

---

## 📦 Model Data

### ApiResponse
```kotlin
data class ApiResponse(
    val area: Area?,
    val id: Int,
    val name: String,
    val shortName: String,
    val tla: String,
    val crest: String,
    val address: String,
    val website: String,
    val founded: Int,
    val clubColors: String,
    val venue: String,
    val coach: Coach?,
    val squad: List<Player>
)
```

### Coach
```kotlin
data class Coach(
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
    val name: String?,
    val dateOfBirth: String?,
    val nationality: String?,
    val contract: Contract?
)

data class Contract(
    val start: String?,
    val until: String?
)
```

### Player
```kotlin
data class Player(
    val id: Int,
    val name: String,
    val position: String?,
    val dateOfBirth: String?,
    val nationality: String?
)
```

---

## 📁 Struktur Project

```
app/src/main/
├── java/com/responsi1mobileh1d023055/
│   ├── MainActivity.kt                    # Halaman utama dengan info klub
│   ├── HistoryActivity.kt                 # Halaman sejarah klub
│   ├── CoachActivity.kt                   # Halaman info pelatih (dari API)
│   ├── PlayersActivity.kt                 # Halaman daftar pemain (dari API)
│   ├── PlayersAdapter.kt                  # Adapter untuk RecyclerView pemain
│   ├── PlayersFragment.kt                 # Fragment (legacy, tidak digunakan)
│   └── data/
│       ├── api/
│       │   ├── FootballApiService.kt      # Interface Retrofit
│       │   └── RetrofitClient.kt          # Singleton Retrofit instance
│       ├── model/
│       │   └── TeamResponse.kt            # Data classes (ApiResponse, Coach, Player)
│       └── repository/
│           └── TeamRepository.kt          # Repository pattern (optional)
│
├── res/
│   ├── layout/
│   │   ├── activity_main.xml              # Layout halaman utama
│   │   ├── activity_history.xml           # Layout sejarah klub
│   │   ├── activity_coach.xml             # Layout info pelatih
│   │   ├── activity_players.xml           # Layout daftar pemain
│   │   ├── item_player.xml                # Item card pemain
│   │   ├── dialog_player_detail.xml       # Bottom sheet detail pemain
│   │   └── fragment_players.xml           # Fragment layout (legacy)
│   │
│   ├── drawable/
│   │   ├── stadium.jpg                    # Foto Borussia-Park
│   │   ├── borussia_m_nchengladbach_logo.png  # Logo klub
│   │   ├── coach_polanski.jpg             # Foto pelatih
│   │   ├── bg_bottom_sheet.xml            # Background bottom sheet (rounded top)
│   │   ├── sports_soccer.xml              # Icon untuk button History
│   │   ├── ic_head_coach.xml              # Icon untuk button Head Coach
│   │   ├── ic_team_player.xml             # Icon untuk button Players
│   │   ├── ic_launcher_background.xml     # Icon launcher background
│   │   └── ic_launcher_foreground.xml     # Icon launcher foreground
│   │
│   ├── values/
│   │   ├── strings.xml                    # String resources
│   │   ├── colors.xml                     # Color resources
│   │   └── themes.xml                     # App themes
│   │
│   └── mipmap-*/
│       └── ic_launcher.png                # App icon (logo Borussia Mönchengladbach)
│
└── AndroidManifest.xml
```

---

## 📚 Library yang Digunakan

### Networking & Data
- **Retrofit 2.9.0** - HTTP client untuk API calls
- **Gson Converter 2.9.0** - JSON parsing
- **OkHttp Logging Interceptor 4.11.0** - Logging network requests

### Asynchronous Programming
- **Kotlinx Coroutines Android 1.7.3** - Asynchronous programming
- **Kotlinx Coroutines Core 1.7.3** - Coroutines core library

### Architecture Components
- **Lifecycle ViewModel KTX 2.6.2** - ViewModel untuk state management
- **Lifecycle LiveData KTX 2.6.2** - LiveData untuk reactive data

### UI Components
- **Material Components 1.9.0+** - Material Design components
- **ConstraintLayout** - Flexible layout system
- **RecyclerView** - Efficient list display
- **CardView** - Card UI component
- **ViewPager2 1.0.0** - Page sliding (legacy, tidak digunakan)
- **Fragment KTX 1.6.2** - Fragment management

### Android Core
- **AndroidX Core KTX** - Kotlin extensions
- **AndroidX AppCompat** - Backward compatibility
- **AndroidX Activity KTX** - Activity extensions

---

## 🎨 Tema & Warna

### Color Palette
- **Primary Color**: `#202020` (Dark gray/black)
- **Text Color**: `#FFFFFF` (White)

### Player Position Colors
- **Goalkeeper**: `#FFEB3B` (Yellow)
- **Defender**: `#2196F3` (Blue)
- **Midfielder**: `#4CAF50` (Green)
- **Forward**: `#F44336` (Red)

### UI Theme
- Background cards menggunakan warna `#202020` (dark theme)
- Text berwarna putih untuk kontras maksimal
- Button menggunakan Material Design dengan corner radius 10dp
- Bottom Sheet dengan rounded top corners (15dp)

---

## 🔧 Konfigurasi

### Build Configuration
- **Namespace**: `com.responsi1mobileh1d023055`
- **Compile SDK**: 36
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **JVM Target**: 11
- **Build Features**: View Binding enabled

### API Configuration
API Token dikonfigurasi di `RetrofitClient.kt`:
```kotlin
private const val API_TOKEN = "90a829184dc64ee59e72a3f2244d90ba"
```

---

## 🚀 Cara Menjalankan Aplikasi

1. **Clone repository** atau buka project di Android Studio
2. **Pastikan API Token valid** di `RetrofitClient.kt`
3. **Sync Gradle** (otomatis saat membuka project)
4. **Build project**: `Build → Make Project`
5. **Run aplikasi**: 
   - Menggunakan emulator, atau
   - Menggunakan device fisik (USB debugging enabled)

---

## 📱 Fitur Aplikasi

### ✅ Data dari API Real-Time
- Informasi klub (founded, venue, address)
- Data pelatih (nama, tanggal lahir, nationality, kontrak)
- Data pemain (nama, posisi, tanggal lahir, nationality)

### ✅ UI/UX Modern
- Material Design 3
- Dark theme dengan kontras tinggi
- Smooth animations
- Bottom Sheet untuk detail pemain
- Progress bar saat loading
- Error handling yang baik

### ✅ Performance
- Asynchronous data loading dengan Coroutines
- View Binding untuk efisiensi
- RecyclerView untuk list performance
- Image caching (jika menggunakan Glide/Coil)

---

## 📝 Catatan Pengembangan

### Perubahan dari Design Awal
1. **Tidak menggunakan TabLayout** untuk pemain - diganti dengan single RecyclerView yang di-sort
2. **Bottom Sheet Dialog** menggantikan AlertDialog biasa untuk tampilan lebih modern
3. **Tema dark** (#202020) untuk semua card content
4. **Semua data dari API** - tidak ada hardcoded data (kecuali foto dan sejarah klub)

### Error Handling
- Progress bar saat loading data
- Error message jika API gagal
- Fallback "N/A" untuk data yang tidak tersedia

---

## 👨‍💻 Developer

**NIM**: H1D023055  
**Klub**: Borussia Mönchengladbach (Team ID: 18)  
**API**: [football-data.org](https://api.football-data.org/)

---

## 📄 License

Project ini dibuat untuk keperluan akademik - Responsi 1 Mobile Programming.

---

**Made with ❤️ using Kotlin & Android Studio**
