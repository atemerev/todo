package ru.fprog.todo

import ru.circumflex._, core._, web._, freemarker._
import java.util.Date

class MainRouter extends RequestRouter {
  post("/api/task") = {
    val text = param("text")
    val task = new TodoEntry(text)
    task.save()
    // возвращаем кусок XML с датой создания и текстом todo-записи
    task.toXml
  }
  
  get("/") = {
    //параметр контекста, который потом передаётся шаблону
    'todo := TodoEntry.todoEntries
    ftl("template.ftl")
  }
  
  post("/api/done") = {
    val id = param("id").toInt
    val task = TodoEntry.byId(id).get 
    task.end := new Date
    task.save()
    "OK"
  }
} 
