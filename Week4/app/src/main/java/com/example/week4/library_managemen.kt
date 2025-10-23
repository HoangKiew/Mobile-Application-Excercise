package com.example.week4

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week4.ui.theme.Week4Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

// ---(DATA)---

//Data class Sách
data class Book(
    val id: String,
    val title: String,
    val isTicked: Boolean
)

//Data class State
data class LibraryUiState(
    val studentName: String = "",
    val displayedBooks: List<Book> = emptyList()
)

// --- VIEWMODEL---

class LibraryViewModel : ViewModel() {

    // --- "DATABASE" GIẢ ---
    private val mockStudentDatabase = mapOf(
        "Nguyen Van A" to listOf(
            Book("1", "Sách 01", true),
            Book("2", "Sách 02", true)
        ),
        "Nguyen Thi B" to listOf(
            Book("1", "Sách 01", true)
        )
    )

    // _uiState là trạng thái riêng tư
    private val _uiState = MutableStateFlow(
        LibraryUiState() // Khởi tạo trạng thái trống
    )

    // uiState là trạng thái công khai
    val uiState = _uiState.asStateFlow()

    fun onStudentNameChange(newName: String) {
        _uiState.update { currentState ->
            LibraryUiState(
                studentName = newName,
                displayedBooks = currentState.displayedBooks
            )
        }
    }

    // ---Xử lý khi nhấn nút "Thay đổi" ---
    fun changeStudent() {
        _uiState.update { currentState ->
            val currentName = currentState.studentName.trim()
            val booksForStudent = mockStudentDatabase.getOrDefault(currentName, emptyList())
            currentState.copy(
                studentName = currentName,
                displayedBooks = booksForStudent
            )
        }
    }

    // ---Xử lý khi tick vào Checkbox ---
    fun toggleBookTick(bookId: String) {
        _uiState.update { currentState ->
            val newBooksList = currentState.displayedBooks.map { book ->
                if (book.id == bookId) {
                    book.copy(isTicked = !book.isTicked)
                } else {
                    book
                }
            }
            currentState.copy(displayedBooks = newBooksList)
        }
    }

    // ---Xử lý khi nhấn nút "Thêm" ---
    fun addBookToStudent() {
        _uiState.update { currentState ->
            val newBook = Book(
                id = UUID.randomUUID().toString(),
                title = "Sách 0${currentState.displayedBooks.size + 1}", // Giữ nguyên logic Sách 0... của bạn
                isTicked = false
            )
            val newList = currentState.displayedBooks + newBook
            currentState.copy(displayedBooks = newList)
        }
    }

    // ---Xử lý khi nhấn nút "Xóa" ---
    fun deleteBook(bookId: String) {
        _uiState.update { currentState ->
            val newList = currentState.displayedBooks.filterNot { it.id == bookId }
            currentState.copy(displayedBooks = newList)
        }
    }
}

// --- GIAO DIỆN (COMPOSABLES) ---

val lightGrayBackground = Color(0xFFF0F0F0)
val darkBlueButton = Color(0xFF0D47A1)
val textFieldBackground = Color(0xFFFFFFFF)
val disabledGray = Color(0xFFBDBDBD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryManagementScreen(
    navController: NavController, //
    viewModel: LibraryViewModel = viewModel()
) {
    // Lắng nghe trạng thái (State) từ ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = lightGrayBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Hệ thống Quản lý Thư viện", fontWeight = FontWeight.Bold) },
                // SỬA: THÊM NÚT QUAY LẠI
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            MyBottomBar()
        },
        floatingActionButton = {
            Button(
                onClick = { viewModel.addBookToStudent() },
                colors = ButtonDefaults.buttonColors(containerColor = darkBlueButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(50.dp)
            ) {
                Text(text = "Thêm", fontSize = 18.sp)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            //thông tin sinh viên
            StudentSection(
                studentName = uiState.studentName,
                onNameChange = { newName ->
                    viewModel.onStudentNameChange(newName)
                },
                onChangeClick = { viewModel.changeStudent() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            //danh sách sách
            BookListSection(
                books = uiState.displayedBooks,
                studentName = uiState.studentName,
                onBookClick = { bookId ->
                    viewModel.toggleBookTick(bookId)
                },
                onBookDelete = { bookId ->
                    viewModel.deleteBook(bookId)
                }
            )
        }
    }
}

@Composable
fun StudentSection(
    studentName: String,
    onNameChange: (String) -> Unit,
    onChangeClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Sinh viên",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = studentName,
                onValueChange = onNameChange,
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground,
                    disabledContainerColor = textFieldBackground,
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onChangeClick,
                colors = ButtonDefaults.buttonColors(containerColor = darkBlueButton)
            ) {
                Text(text = "Thay đổi")
            }
        }
    }
}

@Composable
fun BookListSection(
    books: List<Book>,
    studentName: String,
    onBookClick: (String) -> Unit,
    onBookDelete: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Danh sách sách",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            if (books.isEmpty() && studentName.isNotBlank()) {
                Text(
                    text = "Bạn chưa mượn quyển sách nào.\n...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(books) { book ->
                        BookItem(
                            book = book,
                            onClick = { onBookClick(book.id) },
                            onDelete = { onBookDelete(book.id) }
                        )
                    }
                }
            }
        }
    }
}

// (BookItem giữ nguyên)
@Composable
fun BookItem(
    book: Book,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = book.isTicked,
            onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFC62828)
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = book.title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Xóa sách",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun MyBottomBar() {
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        BottomNavigationItem(
            selected = true,
            onClick = { /* Không làm gì */ },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Quản lý") },
            label = { Text("Quản lý") },
            selectedContentColor = darkBlueButton
        )
        BottomNavigationItem(
            selected = false,
            onClick = { /* TODO: Chuyển sang màn hình DS Sách */ },
            icon = { Icon(Icons.Filled.MenuBook, contentDescription = "DS Sách") },
            label = { Text("DS Sách") },
            unselectedContentColor = disabledGray
        )
        BottomNavigationItem(
            selected = false,
            onClick = { /* TODO: Chuyển sang màn hình Sinh viên */ },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Sinh viên") },
            label = { Text("Sinh viên") },
            unselectedContentColor = disabledGray
        )
    }
}

@Preview(
    showBackground = true,
    name = "Màn hình Quản lý Thư viện",
)
@Composable
fun LibraryManagementScreenPreview() {
    val navController = rememberNavController()
    Week4Theme {
        // Truyền NavController vào
        LibraryManagementScreen(navController = navController)
    }
}