# Minesweeper

## API

- Создание игры
  - POST /api/new
  - Request
  ```json
    {
        "width": 10,
        "height": 10,
        "mines_count": 10
    }
  ```
  - Response
  ```json
    {
      "game_id": "01234567-89AB-CDEF-0123-456789ABCDEF",
      "width": 2,
      "height": 2,
      "mines_count": 1,
      "completed": false,
      "field": [ [ "1", "X"],  
                 [ "1", "1"]]
    }
  ```
- Клик по ячейке
  - POST /api/turn
  - Request
  ```json
    {
      "game_id": "01234567-89AB-CDEF-0123-456789ABCDEF",
      "col": 5,
      "row": 5
    }
  ```
  - Response
  ```json
    {
      "game_id": "01234567-89AB-CDEF-0123-456789ABCDEF",
      "width": 2,
      "height": 2,
      "mines_count": 1,
      "completed": false,
      "field": [ [ "1", "X"],  
                 [ "1", "1"]]
    }
  ```