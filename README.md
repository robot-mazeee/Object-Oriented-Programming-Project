# Description

In this project, I implemented a conceptual hotel, where animals, trees, vets and keepers can interact.

This repository provides tests which can be ran with the following commands:

```
java -Dimport=test.import -Din=test.in -Dout=test.outhyp hva.app.App
diff -b test.out test.outhyp
```

# Propriedades e funcionalidade
Os animais de várias espécies são mantidos por tratadores que são responsáveis por distribuir a comida e pela limpeza dos habitats, e por veterinários, que têm a responsabilidade de zelar pela saúde dos animais. Quando um animal recebe uma vacina não apropriada à sua espécie, por causa de um erro veterinário, por exemplo, fica com a saúde afectada até ao fim da sua vida. Os habitats têm árvores. É possível calcular o grau de satisfação dos animais e dos funcionários.

Em todos os campos identificadores ou nomes, excepto se indicado o contrário, as diferenças entre maiúsculas e minúsculas são irrelevantes.

# Espécies
As espécies são identificadas por uma chave única (cadeia de caracteres arbitrária definida na altura da criação).

Cada espécie tem um nome (cadeia de caracteres única, i.e., não existem duas espécies distintas com o mesmo nome) e um registo dos animais da espécie.

# Animais
Os animais são identificados por uma chave única (cadeia de caracteres arbitrária definida na altura da criação).

Cada animal tem um nome (cadeia de caracteres não única) e informação acerca da sua espécie e do estado de saúde.

É possível calcular o nível de satisfação de um animal. Este valor depende de vários factores (ver a seguir).

# Satisfação dos Animais
A satisfação dos animais é afectada positivamente quando existem mais animais da mesma espécie no mesmo habitat e afectada negativamente quando existem animais de outras espécies. O número total de animais no habitat e o próprio habitat também influenciam a satisfação dos animais que lá vivem.

A satisfação de um animal a no seu habitat h é calculada pela fórmula:

satisfação(a) = 20 + 3 * iguais(a, h) - 2 * diferentes(a, h) + área(h) / população(h) + adequação(a, h)

Nesta equação, iguais conta o número de animais da mesma espécie no habitat (sem contar com a), diferentes é o número de animais de espécies diferentes da de a no habitat, área é a área do habitat e população é a população total do habitat, adequação é 20 se a influência do habitat for positiva, -20 se for negativa e 0 se a influência for neutra (condição por omissão).

# Habitats
Os habitats são identificados por uma chave única (cadeia de caracteres arbitrária definida na altura da criação).

Cada habitat tem um nome (cadeia de caracteres; não única) e informação sobre a área (número inteiro). Os habitats estão associados a zero ou mais árvores e a zero ou mais animais. Estão ainda associados às espécies cuja satisfação influenciam.

Os habitats podem ser mais (ou menos) adequados para determinadas espécies. A adequação tem impacto no grau de satisfação dos animais. Por omissão, um habitat tem um impacto neutro para cada espécie.

# Árvores
As árvores são identificadas por uma chave única (cadeia de caracteres arbitrária definida na altura da criação). Cada árvore tem ainda nome (cadeia de caracteres; não única) e idade em anos (número inteiro). As árvores podem ser de folha caduca ou perene e são caracterizadas pela dificuldade base de limpeza (número inteiro) que induzem no habitat onde estão implantadas (definido no momento da introdução da árvore no habitat).

A vida das árvores segue o ciclo definido pelas estações do ano. Assim, por exemplo, as árvores de folha caduca perdem as folhas principalmente durante o Outono, ficando sem folhas no Inverno. As árvores de folha perene perdem algumas folhas durante todas as estações, mas mais durante o Inverno.

O esforço total de limpeza de uma árvore é assim determinado pelo produto de três factores: o primeiro corresponde à dificuldade base de limpeza da árvore; o segundo depende da estação do ano e do tipo de árvore, tal como indicado na tabela seguinte (função esforço_sazonal); e o terceiro corresponde a um factor que aumenta com a idade da árvore (ver abaixo).


<img width="647" alt="image" src="https://github.com/user-attachments/assets/34a1fb17-d88e-429d-9ffb-3d6b0f8f7b15">



A parte do esforço total de limpeza que é proporcional à idade da árvore cresce logaritmicamente (i.e., não muito rapidamente): log(idade + 1) (logaritmo natural).

Combinando os vários elementos, obtém-se a fórmula para o esforço total de limpeza:

esforço_limpeza(a) = dificuldade_limpeza(a) * esforço_sazonal(a) * log(idade(a) + 1)

Quando uma árvore é criada, fica na estação do ano em que a aplicação estiver no momento da criação. A estação inicial da aplicação é a Primavera.

As árvores envelhecem com as estações, i.e., a idade aumenta em 1 unidade (1 ano) a cada 4 estações (note-se que o incremento não é necessariamente simultâneo em todas as árvores).

# Funcionários
Os funcionários são identificados por uma chave única (cadeia de caracteres arbitrária definida na altura da criação). Os funcionários têm um nome (cadeia de caracteres não única).

Existem dois tipos de funcionários: tratadores e veterinários. Cada tratador está associado aos habitats da sua responsabilidade (zero ou mais). Cada veterinário está associado às espécies que sabe vacinar (zero ou mais).

# Satisfação dos Veterinários
A satisfação dos veterinários é afectada positivamente pela existência de outros veterinários com a mesma responsabilidade (n_veterinários(e)) e negativamente pelo número de animais que estão sob a sua responsabilidade. Apenas se contabilizam as espécies que o veterinário pode tratar (espécies(v)).

A satisfação para um veterinário v é calculada pela fórmula:

satisfação(v) = 20 - trabalho(v)
trabalho(v) = Σ (população(e) / n_veterinários(e)), e ∈ espécies(v)

# Satisfação dos Tratadores
A satisfação dos tratadores é afectada negativamente pelo trabalho que lhes é atribuído. Apenas se contabilizam os habitats sob responsabilidade do tratador (habitats(t)). O trabalho é também influenciado pelas árvores existentes no habitat (árvores(h)) (ver também secção sobre habitats e árvores).

A satisfação para um tratador t é calculada pela fórmula:

satisfação(t) = 300 - trabalho(t)
trabalho(t) = Σ (trabalho_no_habitat(h) / número_de_tratadores_podem_cuidar_habitat(h)), h ∈ habitats(t)
trabalho_no_habitat(h) = área(h) + 3 * população(h) + Σ esforço_limpeza(a), a ∈ árvores(h)

# Problemas associados à vacinação
O estado de saúde de um animal é o histórico de eventos (inicialmente vazio), relacionados com a vacinação do animal, a partir da situação inicial, entre os quais estão os danos causados por más vacinações.

Se a espécie do animal está incluída nas espécies para as quais uma vacina é apropriada, então o valor do dano causado pela vacina é 0 (zero):

dano(v, a) = 0

Caso contrário, os danos causados pela má administração de uma vacina v a um animal a dependem do máximo do número de letras distintas entre o nome da espécie de a (espécie(a)) e os nomes das espécies a que se destinava v (espécies(v)).

dano(v, a) = MAX (tamanho_nomes(espécie(a), e) - caracteres_comuns(espécie(a), e)), e ∈ espécies(v)
tamanho_nomes(espécie1, espécie2) = max(tamanho(espécie1), tamanho(espécie2))

Por exemplo, quando uma ave recebe uma vacina destinada apenas a um mamífero, o dano resultante da má aplicação da vacina é igual a 6.

A seguinte tabela descreve os resultados associados aos potenciais danos causados por vacinações:


<img width="648" alt="image" src="https://github.com/user-attachments/assets/874e5a38-3778-48b4-bcc1-a5a942830204">


Por exemplo, um animal que tenha recebido 2 vacinas com um dano entre 1 e 4 (ACIDENTE), três vacinas com um dano igual ou superior a 5 (ERRO) e uma vacina correctamente aplicada, apresenta o seguinte historial de saúde (usa-se a vírgula como separador): ACIDENTE,ACIDENTE,ERRO,ERRO,ERRO,NORMAL (i.e., concatenação ordenada por ordem de ocorrência).

# Funcionalidade da aplicação
A aplicação permite:

1. Gerir e operar sobre toda a informação relativa aos conceitos acima, incluindo o cálculo da satisfação;
2. Preservar persistentemente o seu estado via serialização Java (não é possível manter várias versões do estado da aplicação em simultâneo);
3. Efectuar pesquisas sujeitas a vários critérios e sobre as diferentes entidades geridas.
4. Carregamento no início da aplicação de uma base de dados textual com conceitos pré-definidos.

Aplicação de um padrão de desenho para melhorar a legibilidade do código relacionado com o conceito árvore e permitir ao mesmo tempo que se possam adicionar novos eventos relacionados com as estações sem que isso implique alterar o código relacionado com árvores. O padrão deve ainda poder suportar novas funcionalidades dependentes da estação actual (por exemplo, qual a cor das folhas de uma árvore, que vai depender do tipo de árvore e da estação actual) sem comprometer a legibilidade da solução.

Aplicação de um padrão de desenho que permita novas políticas de cálculo da satisfação dos funcionários sem impacto no código existente. A solução concretizada deve permitir alterar o cálculo da satisfação de um funcionário em tempo de execução. Quando é criado um funcionário, o cálculo da satisfação segue a fórmula descrita neste enunciado.

Deve ser possível introduzir novos métodos de pesquisa com um impacto mínimo na implementação desenvolvida.

# Interacção com o utilizador
Descreve-se nesta secção a funcionalidade máxima da interface com o utilizador. Em geral, os comandos pedem toda a informação antes de procederem à sua validação (excepto onde indicado). Todos os menus têm automaticamente a opção Sair (fecha o menu).

As operações de pedido e apresentação de informação ao utilizador devem realizar-se através dos objectos form e display, respectivamente, presentes em cada comando. As mensagens são produzidas pelos métodos das bibliotecas de suporte (po-uilib e hva-app). As mensagens não podem ser usadas no núcleo da aplicação (hva-core). Além disso, não podem ser definidas novas. Potenciais omissões devem ser esclarecidas antes de qualquer implementação.

A apresentação de listas (e.g., animais, funcionários, vacinas, etc.) faz-se por ordem crescente da respectiva chave, excepto em caso de indicação contrária. A ordem das chaves alfanuméricas deve ser lexicográfica (UTF-8), não havendo distinção entre maiúsculas e minúsculas.

De um modo geral, sempre que no contexto de uma operação com o utilizador aconteça alguma excepção, então a operação não deve ter qualquer efeito no estado da aplicação, excepto em caso de indicação contrária na operação em causa. As excepções estão na package hva.app.exceptions, excepto em caso de indicação contrária.

As excepções usadas na interacção (subclasses de pt.tecnico.uilib.menus.CommandException), excepto em caso de indicação contrária, são lançadas pelos comandos (subclasses de pt.tecnico.uilib.menus.Command) e tratadas pelos menus (instâncias de subclasses de pt.tecnico.uilib.menus.Menu). Outras excepções não devem substituir as fornecidas nos casos descritos.

Nos pedidos e usos dos vários identificadores, podem ocorrer as seguintes excepções, caso o identificador indicado não corresponda a um objecto conhecido (excepto no processo de registo ou em caso de indicação contrária). Note-se que estas excepções não são utilizáveis no núcleo da aplicação.


<img width="647" alt="image" src="https://github.com/user-attachments/assets/c5534fbf-988a-40a4-a234-69a255c2c1fe">


Alguns casos particulares podem usar pedidos específicos não apresentados nesta tabela.

<h1>Funtionality</h1>

# Main Menu
The main menu allows the user to manage and save the state of the application, perform global operations and open submenus. 

1. Create empty application
2. Open existing application
3. Save application state
4. Advance Season
5. See Satisfaction State, Manage Animals
6. Manage Employees
7. Manage Vaccines
8. Manage Habitats
9. Consults
    
# Check Global Satisfaction
The application returns the sum of all animals' and employees' satisfactions.

# Animal Management Menu

1. View all Animals 
2. Register new Animal
3. Transfer Animal to another Habitat 
4. Calculate Animal Satisfaction
   
# Employee Management Menu

1. View all Employees
2. Register new Employee 
3. Assign a new Responsibility (Habitat to clean or Animal to vaccinate) 
4. Remove an Employee's Responsibility
5. Calculate Employee Satisfaction

# Habitat Management Menu

1. View all Habitats
2. Register new Habitat 
3. Change area of an Habitat 
4. Change Habitat's Influence over a Species
5. Plant a new Tree in Habitat
6. View all Trees in Habitat

# Menu de Gestão de Vacinas
Este menu permite operar sobre vacinas e vacinações. A lista completa é a seguinte: (i) visualizar todas as vacinas; (ii) registar uma nova vacina; (iii) vacinar um animal; (iv) listar o histórico de vacinações. As secções abaixo descrevem estas opções.

As etiquetas das opções deste menu estão definidas em hva.app.vaccine.Label. Todos os métodos correspondentes às mensagens de diálogo para este menu estão definidos em hva.app.vaccine.Prompt e hva.app.vaccine.Message.
Estes comandos já estão implementados nas classes da package hva.app.animal (disponível no GIT), respectivamente: DoShowVaccine, DoRegisterVaccine, DoVaccinateAnimal, DoShowVaccinations.

# Visualizar todas as vacinas
O formato de apresentação de cada vacina, uma por linha, é o seguinte:

 VACINA|idVacina|nomeVacina|númeroDeAplicações|espécies

O campo espécies contém os identificadores, separados por vírgulas, das espécies de animais que podem receber a vacina.

# Registar uma nova vacina
O sistema pede o identificador da vacina, o nome (Prompt.vaccineName()) (cadeia de caracteres) da vacina e os identificadores das espécies que podem receber esta vacina (Prompt.listOfSpeciesKeys()) (lista de identificadores separados por vírgulas -- os espaços brancos são irrelevantes nesta resposta). De seguida regista a nova vacina.

Deve ser lançada a excepção DuplicateVaccineKeyException, se existir uma vacina com o mesmo identificador, não se realizando qualquer acção.

Se algum dos identificadores de espécies indicados for desconhecido, deve ser lançada a excepção UnknownSpeciesKeyException, não sendo registada a nova vacina.

# Vacinar um animal
O sistema pede o identificador da vacina, o identificador do veterinário (Prompt.veterinarianKey()) e o identificador do animal a vacinar. Se o identificador não for de um veterinário, deve ser lançada a excepção UnknownVeterinarianKeyException. Se o veterinário não tiver permissão para ministrar a vacina, deve ser lançada a excepção VeterinarianNotAuthorizedException e o comando não tem qualquer efeito. Se a vacina não for adequada ao animal, deve ser apresentada uma mensagem de aviso (Message.wrongVaccine()). Note-se que, neste caso, a vacinação acontece e os danos para o animal são registados.

# Listar o histórico de vacinações
O sistema lista todas as vacinas aplicadas, por ordem de aplicação, usando o seguinte formato:

 REGISTO-VACINA|idVacina|idVeterinário|idEspécie

# Menu de Consultas
O menu de consultas permite efectuar pesquisas sobre as entidades do domínio e suas relações. Sempre que for feita uma consulta e nenhuma entidade satisfizer as condições associadas ao pedido, nada deve ser apresentado.

A lista completa é a seguinte: (i) consultar animais de um habitat; (ii) consultar vacinas de um animal; (iii) consultar actos médicos de um veterinário; (iv) consultar vacinas dadas com incúria. As secções abaixo descrevem estas opções.

As etiquetas das opções deste menu estão definidas em hva.app.search.Label. Todos os métodos correspondentes às mensagens de diálogo para este menu estão definidos em hva.app.search.Prompt e hva.app.search.Message.
Estes comandos já estão implementados nas classes da package hva.app.search (disponível no GIT), respectivamente: DoShowAnimalsInHabitat, DoShowMedicalActsOnAnimal, DoShowMedicalActsByVeterinarian, DoShowWrongVaccinations.

# Consultar animais de um habitat
O sistema pede o identificador do habitat, apresentando os animais que residem nesse habitat.

O formato de apresentação é como indicado para a listagem de animais.

# Consultar vacinas de um animal
O sistema pede o identificador do animal, apresentando as vacinações (por ordem de aplicação).

O formato de apresentação é como indicado para a listagem de vacinações.

# Consultar actos médicos de um veterinário
O sistema pede o identificador de um funcionário. Se o identificador não existir ou não for de um veterinário, deve ser lançada a excepção UnknownVeterinarianKeyException. Caso contrário, são apresentadas todas as vacinações por ele realizadas (por ordem de aplicação).

O formato de apresentação é como indicado para a listagem de vacinações.

# Consultar vacinas dadas com incúria
O sistema apresenta todas as ocorrências de vacinações que provocaram problemas de saúde aos animais.

O formato de apresentação é como indicado para a listagem de vacinações (por ordem de aplicação).

# Inicialização por Ficheiro de Dados Textuais
Por omissão, quando a aplicação começa, não contém nenhuma informação e está na estação Primavera. No entanto, além das opções de manipulação de ficheiros descritas no menu principal, é possível iniciar exteriormente a aplicação com um ficheiro de texto especificado pela propriedade Java import. Quando se especifica esta propriedade, a aplicação é povoada com os objectos correspondentes ao conteúdo do ficheiro textual indicado.

No processamento destes dados, assume-se que não existem entradas mal-formadas e assume-se que os identificadores referidos numa entrada já foram previamente descritos. Sugere-se a utilização do método String.split() para dividir uma cadeia de caracteres em campos.

No formato abaixo: para os habitats, só os 4 primeiros campos são obrigatórios, correspondendo os opcionais a árvores implantadas no recinto; para os tratadores, só os 3 primeiros campos são obrigatórios, correspondendo os opcionais a habitats; e, para os veterinários e vacinas, só os 3 primeiros campos são obrigatórios, correspondendo os opcionais a espécies de animais. Se alguma das listas for vazia, então não se apresenta o último campo (nem o separador).

# Formato do ficheiro de entrada textual

<img width="196" alt="image" src="https://github.com/user-attachments/assets/7d207b7f-f346-4dfe-a7ed-36b997bb4cad">


# Exemplo de ficheiro de entrada textual: ficheiro test.import


<img width="250" alt="image" src="https://github.com/user-attachments/assets/d55c5664-d4b9-4f98-adb0-6ce554e25f91">


Ver abaixo a forma de utilizar estes ficheiros.

A codificação dos ficheiros a ler é garantidamente UTF-8.
Note-se que o programa nunca produz ficheiros com este formato.

