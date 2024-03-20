package com.example.crudrapido.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.example.crudrapido.entity.Student;
import com.example.crudrapido.service.StudentService;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    // listar estudiantes
    @RequestMapping({ "/students", "/" })
    public String students(Model model) {
        model.addAttribute("students", studentService.getStudents());
        return "students";
    }

    // agregar estudiante
    @GetMapping("/students/nuevo")
    public String crearStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "crear_student";
    }

    // Registrar
    @PostMapping("/students")
    public String guardarStudent(@ModelAttribute("student") Student student) {
        studentService.saveOrUpdate(student);
        return "redirect:/students";
    }

//     // // consultar estudiante por id
//     @GetMapping("/{studentId}")
// public ResponseEntity<Student> getBId(@PathVariable("studentId") Long studentId) {
//     Student student = studentService.getStudent(studentId);
//     if (student != null) {
//         return ResponseEntity.ok(student);
//     } else {
//         return ResponseEntity.notFound().build();
//     }
// }


    // crear o actualizar estudiante
    @PostMapping
    public void saveUpdate(@RequestBody Student student) {
        studentService.saveOrUpdate(student);
    }

    @GetMapping("/students/editar/{id}")
public String showStudent(@PathVariable Long id, Model model) {
    Student student = studentService.getStudent(id);
    if (student != null) {
        model.addAttribute("student", student);
        return "editar_student";
    } else {
        // Manejar el caso en el que no se encuentra el estudiante
        // Puedes redirigir a una página de error o realizar alguna otra acción
        return "redirect:/students"; // Por ejemplo, redirige de vuelta a la página de estudiantes
    }
}


   @PostMapping("/students/{id}")
public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student updatedStudent) {
    // Obtener el estudiante existente
    Student existingStudent = studentService.getStudent(id);
    if (existingStudent != null) {
        // Actualizar los datos del estudiante existente con los datos del estudiante actualizado
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setEmail(updatedStudent.getEmail());

        // Guardar los cambios en el estudiante existente
        studentService.saveOrUpdate(existingStudent);
    } else {
        // Manejar el caso en el que no se encuentra el estudiante
        // Puedes redirigir a una página de error o realizar alguna otra acción
        // Por ejemplo, redirigir de vuelta a la página de estudiantes
        return "redirect:/students";
    }

    return "redirect:/students";
}




    // eliminar estudiante
    @GetMapping("/students/{studentId}")
    public String Delete(@PathVariable Long studentId) {
        studentService.delete(studentId);
        return "redirect:/students";
    }
}
