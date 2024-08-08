<h1>Transações Bancarias em Microserviços</h1>
<p>Por motivos didáticos, criei uma pequena aplicação que simula uma transação bancária, com 3 Microsserviços que se comunicam com RabbitMQ. A ideia deste projeto é realizar uma transferência de dinheiro e notificar o Beneficiário sobre está transação.</p>
<h2>Funcionalidades do Projeto:</h2>
<ol>
  <li>Cadastrar Usuários</li>
  <li>Realizar uma Transferência</li>
  <li>Validação de tipos de Usuários (Lojistas não podem transferir dinheiro)</li>
  <li>Debitar e Creditar o valor da Transferência para o Usuário e Beneficiário</li>
  <li>Notifica o Beneficiário quando uma Transação foi aprovada - Gmail - JavaMailSender</li>
  <li>Dados da notificação por E-mail: <strong>ID da Transação, Nome do Pagador, Nome do Beneficiário, Valor da Transferência, Data e Hora da Transação</strong></li>
</ol>
<h2>Arquitetura da Aplicação:</h2>
<img src="https://github.com/user-attachments/assets/aee6d6ab-db92-4bc6-96f8-f550751cb746">
