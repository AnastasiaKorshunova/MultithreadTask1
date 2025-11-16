# JAVA. MULTITHREADING / TASK 1

---

## Tasks

### 1. Creating a Thread

Write a program that creates a thread.  
Use default thread attributes.  
Both the parent thread and the newly created thread must print **N lines of text**.

---

### 2. Thread @ Runnable

1. Create a `T1Thread` class based on `Thread` with an implemented `run()` method.
2. Create a class that implements `Runnable` with its own `run()` method.
3. Create a thread object of type `T1Thread`, initializing its constructor with an instance of the class that implements `Runnable`.
4. Start the thread and **explain the result**.

---

### 3. The `join` Method

Modify the program from task 1 so that the **parent thread** prints its output  
**after the created thread has finished**.  
Use the `join()` method.

---

### 4. Thread Parameters

Develop a program that creates **N threads** (`4 <= N <= 6`)  
executing **the same method**.

- The method must output a sequence of text lines passed as a parameter.
- Each created thread must output a **different** sequence of lines.

---

### 5. `InterruptedException`

Create a situation where an `InterruptedException` occurs.

- Trigger an interruption of a thread (for example, during `sleep()` or `wait()`).
- Explain the **reason** why this exception is generated.

---

### 6. Priority

Evaluate the execution speed difference between threads with different priorities.

You must:

- Run threads with different priority levels.
- Observe and describe the behavior (which thread runs faster/more often).

---

### 7. The `yield` Method

Evaluate the performance difference between two threads,  
one of which uses the `yield()` method.

You must:

- Create two threads performing similar work.
- Periodically call `Thread.yield()` inside one of them.
- Compare their behavior and describe the results.

---

### 8. (*) Producer / Consumer

Develop a program that creates and runs two threads:  
**P (Producer)** and **C (Consumer)**.

#### Thread P (Producer):

- switches a boolean state (`true` ↔ `false`)  
  with a delay of **M milliseconds**.

#### Thread C (Consumer):

- waits until the Producer’s flag becomes `true`;
- prints a **countdown from K milliseconds**  
  with a delay of **M/10 milliseconds**;
- pauses its execution whenever the Producer’s flag switches to `false`.

#### Termination Condition:

- the countdown reaches zero (**K → 0**).


