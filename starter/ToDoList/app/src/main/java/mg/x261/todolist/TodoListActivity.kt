package mg.x261.todolist

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * TodoListActivity is an Activity for creating a simple to-do list app.
 * It allows the user to add new tasks and remove previously added tasks using a ListView.
 *
 * The activity contains:
 * - An EditText for entering new tasks
 * - A ListView for displaying the tasks
 * - A Button for adding new tasks
 *
 * The user can add new tasks by entering the task in the EditText and clicking the "Add Task" button.
 */


/**
 * This first part of the TodoListActivity code only gives a way to input new task
 */

/**
class TodoListActivity : AppCompatActivity() {

    private lateinit var tasks: ArrayList<String>
    private lateinit var taskListView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        tasks = ArrayList()
        taskListView = findViewById(R.id.task_list)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks)
        taskListView.adapter = adapter

        val addTaskButton = findViewById<Button>(R.id.add_task_button)
        addTaskButton.setOnClickListener {
            val taskInput = findViewById<EditText>(R.id.task_input)
            val task = taskInput.text.toString()
            tasks.add(task)
            adapter.notifyDataSetChanged()
            taskInput.setText("")
        }

        taskListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            tasks.removeAt(i)
            adapter.notifyDataSetChanged()
        }
    }
}
 */

/**
 * This second part of the TodoListActivity code gives a way to both input and remove tasks (long click on the item)
 */

/**
 * TodoListActivity is an Activity for creating a simple to-do list app.
 * It allows the user to add new tasks and remove previously added tasks using a ListView.
 *
 * The activity contains:
 * - An EditText for entering new tasks
 * - A ListView for displaying the tasks
 * - A Button for adding new tasks
 *
 * The user can add new tasks by entering the task in the EditText and clicking the "Add Task" button.
 * The user can remove tasks by clicking on them or long-pressing on them
 *
 * When the user long-presses on a task, the task will be removed and a toast message with the text "Task removed: [task]" will be shown
 */
class TodoListActivity : AppCompatActivity() {

    private lateinit var tasks: ArrayList<String>
    private lateinit var taskListView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    /**
     * onCreate is called when the activity is created
     * It sets the content view, initializes the tasks list, taskListView, and adapter,
     * and sets the adapter for the taskListView.
     *
     * It also sets an onClickListener on the addTaskButton, which adds the task entered in the EditText to the tasks list
     * and updates the adapter when the button is clicked.
     *
     * It also sets an onItemClickListener and onItemLongClickListener on the taskListView
     * which removes the task from the tasks list and shows a toast message with the text "Task removed: [task]"
     * when the user clicks or long-presses on a task, respectively and updates the adapter to reflect the change.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        tasks = ArrayList()
        taskListView = findViewById(R.id.task_list)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks)
        taskListView.adapter = adapter

        val addTaskButton = findViewById<Button>(R.id.add_task_button)
        addTaskButton.setOnClickListener {
            val taskInput = findViewById<EditText>(R.id.task_input)
            val task = taskInput.text.toString()
            tasks.add(task)
            adapter.notifyDataSetChanged()
            taskInput.setText("")
        }

        taskListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            tasks.removeAt(i)
            adapter.notifyDataSetChanged()
        }

        // Long press an item to remove it
        /**
        taskListView.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, i, _ ->
            tasks.removeAt(i)
            adapter.notifyDataSetChanged()
            true
        }
        */

        // TODO 1 DONE: long press an item to remove then show a toast

        // This code is setting an OnItemLongClickListener on the taskListView
        // which is triggered when the user long-presses on a task.
        // The listener is defined as a lambda function that takes four parameters: parent, view, position, and id.
        // However, in this case, the code is not using any of those parameters, so they are replaced with _ which means that the values are ignored.
        //
        // When the user long-presses on an item in the taskListView, the lambda function will be called.
        // Inside the function, the code first retrieves the task that was long-pressed on by using the position parameter,
        // which is the index of the item in the tasks list, and assigns it to the variable task.
        //
        // Then it calls Toast.makeText(this, "Task removed: $task", Toast.LENGTH_SHORT).show()
        // which creates a toast message with the text "Task removed: [task]" where task is the task that was removed,
        // and shows it to the user.
        //
        // After that, it removes the task from the tasks list using the removeAt(i) method,
        // and updates the adapter using notifyDataSetChanged() to reflect the change.
        //
        // Finally, it returns true to indicate that the long-click event was handled,
        // and the event is not propagated to other views.
        //
        // This listener is responsible for providing the user with a feedback when they long-press on a task,
        // which is a Toast message that shows the task that removed, also it removes the task from the tasks list
        // and updates the adapter to reflect this change.
        taskListView.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, i, _ ->
            val task = tasks[i]
            Toast.makeText(this, "Task removed: $task", Toast.LENGTH_SHORT).show()
            tasks.removeAt(i)
            adapter.notifyDataSetChanged()
            true
        }
    }
}