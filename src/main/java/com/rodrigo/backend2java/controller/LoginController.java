package com.rodrigo.backend2java.controller;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        LoginResponse user = LoginService.login(
            authRequest.getId(),
            authRequest.getSenha()
        );

        if (user != null) {
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Usuário autenticado com sucesso", user)
            );
        } 
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Usuário ou senha inválidos", null));
        }
    }

    @GetMapping("/login/{id}/{senha}")
    public ResponseEntity<?> login(@PathVariable String id, @PathVariable String senha) {
        LoginResponse user = loginService.buscarUsuario(
                Integer.parseInt(id),
                senha
        );

        if (user != null) {
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Usuário autenticado com sucesso", user)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Usuário ou senha inválidos", null));
        }
    }
}
