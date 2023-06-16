package net.javaguides.springboot.tutorial.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.springboot.tutorial.entity.Empregado;
import net.javaguides.springboot.tutorial.repository.EmpregadoRepository;

@Controller
@RequestMapping("/empregados/")
public class EmpregadoController {

	private final EmpregadoRepository empregadoRepository;

	@Autowired
	public EmpregadoController(EmpregadoRepository empregadoRepository) {
		this.empregadoRepository = empregadoRepository;
	}

	@GetMapping("signup")
	public String showSignUpForm(Empregado empregado) {
		return "add-empregado";
	}

	@GetMapping("list")
	public String showUpdateForm(Model model) {
		model.addAttribute("empregados", empregadoRepository.findAll());
		return "index";
	}

	@PostMapping("add")
	public String addEmpregado(@Valid Empregado empregado, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-empregado";
		}

		empregadoRepository.save(empregado);
		return "redirect:list";
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Empregado empregado = empregadoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid empregado Id:" + id));
		model.addAttribute("empregado", empregado);
		return "update-empregado";
	}

	@PostMapping("update/{id}")
	public String updateEmpregado(@PathVariable("id") long id, @Valid Empregado empregado, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			empregado.setId(id);
			return "update-empregado";
		}

		empregadoRepository.save(empregado);
		model.addAttribute("empregados", empregadoRepository.findAll());
		return "index";
	}

	@GetMapping("delete/{id}")
	public String deleteEmpregado(@PathVariable("id") long id, Model model) {
		Empregado empregado = empregadoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid empregado Id:" + id));
		empregadoRepository.delete(empregado);
		model.addAttribute("empregados", empregadoRepository.findAll());
		return "index";
	}
}
