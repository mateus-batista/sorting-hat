package br.ufsc.bridge.sortinghat;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import br.ufsc.bridge.sortinghat.model.Aluno;
import br.ufsc.bridge.sortinghat.repository.AlunoRepository;

@Controller
public class LoginController {

	private static String authorizationRequestBaseUri = "oauth2/authorization";
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping("/sorteador")
	public String sorteador(Model model) {
		return "sorteador";
	}

	@GetMapping("/login")
	public String getLoginPage(Model model) {
		Iterable<ClientRegistration> clientRegistrations = null;
		ResolvableType type = ResolvableType.forInstance(this.clientRegistrationRepository)
				.as(Iterable.class);
		if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
			clientRegistrations = (Iterable<ClientRegistration>) this.clientRegistrationRepository;
		}

		clientRegistrations
				.forEach(registration -> this.oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
		model.addAttribute("urls", this.oauth2AuthenticationUrls);

		return "login";
	}

	@GetMapping("/resultado")
	public String getIndex(Model model, OAuth2AuthenticationToken authentication) {

		if (authentication == null) {
			return "redirect:/login";
		}

		OAuth2AuthorizedClient client = this.authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

		String userInfoEndpointUri = client.getClientRegistration()
				.getProviderDetails()
				.getUserInfoEndpoint()
				.getUri();

		if (!StringUtils.isEmpty(userInfoEndpointUri)) {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());

			HttpEntity<String> entity = new HttpEntity<>("", headers);

			ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
			Map userAttributes = response.getBody();

			Aluno aluno = this.alunoRepository.findByEmail(userAttributes.get("email").toString());
			if (aluno != null) {
				model.addAttribute("casa", aluno.getCasa());
				List<Aluno> alunos = this.alunoRepository.findByCasa(aluno.getCasa());
				Collections.sort(alunos);
				model.addAttribute("alunos", alunos);
			}
		}

		return "index";
	}

	@GetMapping("/login_failure")
	public String login_failure(Model model) {
		return "login_failure";
	}

}