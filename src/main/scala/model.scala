package ru.fprog.todo

import ru.circumflex.orm._
import java.util.Date
import java.text.SimpleDateFormat

// соотвествует таблице todo_entry в базе данных
class TodoEntry extends Record[Int, TodoEntry] with IdentityGenerator[Int, TodoEntry] {

  // поля в таблице объявляются почти как в SQL; id генерируется автоинкрементом
  val id = "id".INTEGER.NOT_NULL.AUTO_INCREMENT
  // текст записи
  val text = "text".VARCHAR(255).NOT_NULL
  // время создания записи
  val start = "start".TIMESTAMP.NOT_NULL
  // время пометки записи как «выполненной»
  val end = "end".TIMESTAMP

  // конструктор инициализирует поля с помощью специального оператора :=
  def this(text: String) {
    this()
    this.text := text
    this.start := new Date
  }
  
  // указываем первичный ключ
  def PRIMARY_KEY = id
  
  // и companion object, где прописаны индексы и прочие объекты БД, относящиеся к таблице
  def relation = TodoEntry

  // если запись помечена как «выполнена», у неё определено время выполнения
  def isDone: Boolean = this.end.get.isDefined
  // метод, который «выполняет» задачу, присваивая полю end текущее время
  def markDone() { this.end := new Date }

  // экспортируем в XML, точнее, в XHTML. По-хорошему, нужно сериализовать в JSON и 
  // работать с шаблонами на стороне клиента, но пока «шаблон» будет прямо здесь:
  val sdf = new SimpleDateFormat("MMM dd, yyyy")
  def toXml = <li id={"l" + this.id()}>
                {sdf.format(this.start())}: {this.text()} 
                <button class="done" id={"d" + this.id()}>Done</button>
              </li>
}

// объект-компаньон с дополнительными сведениями
object TodoEntry extends TodoEntry with Table[Int, TodoEntry] {
  // определяем два индекса на полях start и end — пригодятся
  val idxStart = "idx_start".INDEX("start")
  val idxEnd = "idx_end".INDEX("end")
  
  def todoEntries = {
    val te = TodoEntry AS "te"
    SELECT(te.*) FROM (te) WHERE (te.end IS_NULL) list
  }
  
  def byId(id: Int) = {
    val te = TodoEntry AS "te"
    SELECT(te.*) FROM (te) WHERE (te.id EQ id) unique
  }
  
}

object CreateSchema {
  def main(args: Array[String]) {
    val ddl = new DDLUnit(TodoEntry)
    ddl.DROP_CREATE
  }
}
