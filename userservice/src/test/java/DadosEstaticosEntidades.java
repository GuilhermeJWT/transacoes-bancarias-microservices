import lombok.Getter;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles(value = "test")
@Getter
public class DadosEstaticosEntidades {

    public List<String> mensagemErro(){
        List<String> msgErro = new ArrayList<>();

        String beneficiarioNaoEncontrado = "Beneficiario não Encontrado!";
        String camposDuplicados = "Campos já cadastrados na base de dados, Por Favor, Informe outros!";
        String metodoHttpNaoSuportado = "Tipo de solicitação HTTP incorreta, reveja qual o tipo correto: 'GET' 'POST' 'PUT' 'DELETE' ou outro!";
        String payloadInexistente = "Payload da Requisição Inexistente, informe os campos Válidos!";
        String usuarioNaoEncontrado = "Usuário não Encontrado!";
        String valorTransacaoMaior = "Valor da Transação é maior que o Saldo Atual da Conta!";

        msgErro.add(beneficiarioNaoEncontrado);
        msgErro.add(camposDuplicados);
        msgErro.add(metodoHttpNaoSuportado);
        msgErro.add(payloadInexistente);
        msgErro.add(usuarioNaoEncontrado);
        msgErro.add(valorTransacaoMaior);

        return msgErro;
    }
}
