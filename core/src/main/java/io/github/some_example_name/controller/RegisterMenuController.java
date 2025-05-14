package io.github.some_example_name.controller;

import io.github.some_example_name.view.Menu;
import io.github.some_example_name.view.RegisterMenu;

public class RegisterMenuController {
    private final Menu view;
    public RegisterMenuController(RegisterMenu menu){
        this.view = menu;
    }

    public Menu getView() {
        return view;
    }
}
