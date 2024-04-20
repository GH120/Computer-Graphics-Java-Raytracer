# Instalação e Execução
Precisa apenas do Java e JDK instalados

No Vscode precisa ser executado manualmente tem problemas de compilação devido aos pacotes, mas no InteliJ funciona perfeitamente.

Para escolher a cena,o modelo de renderização e as dimensões da imagem, basta mudar no main:

![image](https://github.com/GH120/Computer-Graphics-Java-Renderer/assets/90730714/cebcb917-bb62-4c20-8a19-a7a18c96d0de)

# Features:
- Raytracing usando MonteCarlo
- Importação de Modelos OBJ
- Raycasters, Raytracers, Raytracers paralelos e Raytracers com iluminação global
- Fontes pontuais, direcionais, spot e extensas
- Reflexões Especulares, Glossy e Refração
- Texturas como wrappers para objetos
- Matrizes e aplicação de matrizes em malhas (Escala, translação, rotação ,cisalhamento...)
- Clusterização para otimização de malhas extensas usando BVH (conseguimos importar malhas com mais de 25000 polígonos em tempo logarítimico)
- Customização de Cenas e Objetos

# Customização

Cenas são altamente customizaveis, basta criar uma classe que herda de cena e escolher suas configurações no construtor

- Os objetos são configurados chamando a função Objetos(...) que aceita n objetos separados por vírgula.
- As fontes pontuais são configuradas chamando a função Fontes(...) que aceita n fontes separadas por vírgula.
- As fontes extensas são configuradas chamando a função FontesExtensas(...) que aceita n fontes separadas por vírgula.
- As cameras são configuradas chamando a função Cameras(...) que aceita n cameras separadas por vírgula.

Qualquer dúvida melhor copiar uma cena existente e modificar pontualmente
-

![image](https://github.com/GH120/Computer-Graphics-Java-Renderer/assets/90730714/2d98d052-2321-45d1-9888-1bd6bb80e483)
![image](https://github.com/GH120/Computer-Graphics-Java-Renderer/assets/90730714/800c39c5-f43c-4597-ae63-bce5e7ffa258)
![image](https://github.com/GH120/Computer-Graphics-Java-Renderer/assets/90730714/58652eac-789e-4b11-a13f-274cbe84d61e)


# Funcionalidade do modelo de renderização
![Modelo de Renderização](https://user-images.githubusercontent.com/90730714/234678379-7ff2dcb5-c83b-4cc2-b544-684c9ed79d23.jpeg)

# Hierarquia das classes de objetos
![Hierarquia dos Props - Página 1](https://user-images.githubusercontent.com/90730714/234388665-5daac82f-60a3-4b35-9db5-0f9f8898c9a1.jpeg)

# Exemplos de cenas:

![Glossy2](https://user-images.githubusercontent.com/90730714/235527751-5f8c7479-cd41-4191-a6bc-07bcf8bc9c87.png)
![Misto](https://user-images.githubusercontent.com/90730714/235528338-fd19e706-d53d-40f2-820f-b6ac32cc2b86.png)
![Especular1](https://user-images.githubusercontent.com/90730714/235527741-3ffda6aa-d1b7-448c-a82c-56443a683a4c.png)
![Glossy1](https://user-images.githubusercontent.com/90730714/235527747-a832fbd5-f256-4de3-91c1-bd4fae85c0da.png)
![Captura de tela 2023-06-20 183640](https://github.com/GH120/CG-Java-Renderer/assets/90730714/673b8633-ea50-40f7-979a-be3d9139671d)
![Captura de tela 2023-07-17 152508](https://github.com/GH120/CG-Java-Renderer/assets/90730714/b0dba2ca-a286-4047-99aa-37f549f2ab80)
![Captura de tela 2023-07-18 123425](https://github.com/GH120/CG-Java-Renderer/assets/90730714/b05711c8-96ff-41b9-a90a-92393cd05edc)
![Captura de tela 2023-07-18 140806](https://github.com/GH120/CG-Java-Renderer/assets/90730714/6a1c0814-4c4d-4321-9ae4-dae15d902184)
![Captura de tela 2023-07-19 163048](https://github.com/GH120/CG-Java-Renderer/assets/90730714/6c9a03a8-3cab-4d64-be17-5e8978f89895)
![Captura de tela 2023-07-19 192906](https://github.com/GH120/CG-Java-Renderer/assets/90730714/361b99f4-91e2-4c71-9882-abffd91b3463)
![Captura de tela 2023-07-20 125959](https://github.com/GH120/CG-Java-Renderer/assets/90730714/87f31e7c-8373-423d-9395-ac373c58603c)
![Captura de tela 2023-07-20 225352](https://github.com/GH120/CG-Java-Renderer/assets/90730714/07195ee6-5537-467d-bac1-3e0c7d06b65e)

![Captura de tela 2023-07-21 223956](https://github.com/GH120/CG-Java-Renderer/assets/90730714/807d2ad7-8312-425a-bd66-d4b7319e87b7)
![Captura de tela 2023-07-22 103155](https://github.com/GH120/CG-Java-Renderer/assets/90730714/3750907b-f830-4cb2-a677-b64415ca741f)
![Captura de tela 2023-07-22 112434](https://github.com/GH120/CG-Java-Renderer/assets/90730714/0d3a6665-a34e-4f75-8427-fb4ee9a90763)

