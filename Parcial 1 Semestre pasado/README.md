# Math Challenge
El juego consiste en un juego de competencia de dos jugadores en el que cada uno debe
responder una serie de preguntas de aritmética y gana quien finaliza primero el reto.
Debe entonces desarrollar una ronda del juego. Las fases son las siguientes:

1. El servidor espera por dos jugadores. Cuando los dos clientes se conectan, comienza el
juego.
2. Cada jugador recibe una ronda de 5 preguntas de aritmética. Las preguntas para cada
jugador deben ser las mismas para que la competencia tenga sentido.
3. Cada jugador va respondiendo sus preguntas gracias a un diseño de pantalla simple
4. El jugador que responda correctamente, puede ver su siguiente pregunta y así
sucesivamente hasta llegar a la última pregunta.
5. El jugador que se equivoque no podrá seguir hasta que responda la pregunta bien.
6. El jugador que termine de primero podrá ver en pantalla que efectivamente ganó la
competencia.
7. El juego también termina para el perdedor indicándole en la pantalla que efectivamente
perdió.

## Ponits

1. Modelo MVC para la interfaz gráfica (1 punto).						<font color = "blue">Done</font> 
2. El servidor es capaz de iniciar el juego cuando hay dos jugadores (1 punto).		<font color = "blue">Done</font> 
3. Generación de un único cuestionario para ambos contrincantes (1 punto). Hay 0.25
puntos por cada una de las operaciones básicas.						<font color = "blue">Done</font> 
4. Uso de mensajes JSON (1 punto)								<font color = "blue">Done</font> 
5. Adecuado modelamiento de mensajes a transmitir: tiene un modelo para cada
mensaje distinto a transmitir en el programa. (1 punto).					<font color = "blue">Done</font> 
### Bonus
6. Previene las conexiones de un tercer, cuarto, quinto, etc. En otras palabras, sólo deja
conectar a dos clientes. (0.2 puntos)								<font color = "blue">Done</font> 
7. Cada jugador ve por cuál pregunta va su contrincante.					<font color = "blue">Done</font> 



