package com.tacos.controller;

import com.tacos.entity.Ingredient;
import com.tacos.entity.Ingredient.Type;
import com.tacos.entity.Taco;
import com.tacos.entity.TacoOrder;
import com.tacos.repository.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder") // this line gives annotations red highlight
public class DesignTacoController {
	private IngredientRepository ingredientRepository;

	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		Iterable<Ingredient> ingredients = ingredientRepository.findAll();
		Type[] types = Ingredient.Type.values();

		for (Type type : types) {
			model.addAttribute(
					type.toString().toLowerCase(),
					filterByType((List<Ingredient>) ingredients, type));
		}
	}

	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order() {
		return new TacoOrder();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@GetMapping
	public String showDesignForm() {
		return "design";
	}

	@PostMapping
	public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {

		if (errors.hasErrors()) {
			return "design";
		}

		tacoOrder.addTaco(taco);
		log.info("Processing taco: {}", taco);

		return "redirect:/orders/current";
	}

	private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
}
