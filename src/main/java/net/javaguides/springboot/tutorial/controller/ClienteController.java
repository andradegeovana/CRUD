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

import net.javaguides.springboot.tutorial.entity.Cliente;
import net.javaguides.springboot.tutorial.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes/")
public class ClienteController {

	private final ClienteRepository clienteRepository;

	@Autowired
	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("signup")
	public String showSignUpForm(Cliente cliente) {
		return "add-cliente";
	}

	@GetMapping("list")
	public String showUpdateForm(Model model) {
		model.addAttribute("clientes", clienteRepository.findAll());
		return "index";
	}

	@PostMapping("add")
	public String addCliente(@Valid Cliente cliente, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-cliente";
		}

		clienteRepository.save(cliente);
		return "redirect:list";
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid cliente Id:" + id));
		model.addAttribute("cliente", cliente);
		return "update-cliente";
	}

	@PostMapping("update/{id}")
	public String updateCliente(@PathVariable("id") long id, @Valid Cliente cliente, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			cliente.setId(id);
			return "update-cliente";
		}

		clienteRepository.save(cliente);
		model.addAttribute("clientes", clienteRepository.findAll());
		return "index";
	}

	@GetMapping("delete/{id}")
	public String deleteCliente(@PathVariable("id") long id, Model model) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid cliente Id:" + id));
		clienteRepository.delete(cliente);
		model.addAttribute("clientes", clienteRepository.findAll());
		return "index";
	}
}
