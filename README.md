# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

##Link to ChessDiagram:
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5T9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdOVwgBRBTSGB04CjGAAM28w35HUwjOJLJOoNxKhg5S5UG8ZRgSDQVGAyA41KGgsMOJEityp0BF3VKrV5U12t1+rumAtwLlhSOlxg-Wt0HKOshwA4YXkAGt0AcPSDTtlzOUACxOADM3W9qt9MH9KEDweAYbQfQO0uZ6jdRRNKHKZB8xdU5QAajqkBwYPrbobjVQ8aciyTazAQAHMq3aR07rMXm8OLMftsGdoZSXTsZynBuQp6lyJQbRuP9K8ltPNtsYABJABy9RqW6gHa7BPnNfKA6zmQUPjAqRH25Qc6JNbZy7wGuG7XmOGbvqk9QQHmp4XlewAQVKD69gBHIRDyfICju4EflBMHnpeOGpEhf4oWa7oKhWRF4egt6mua5zApyUJIlAkSqD4CBYC6lClpGVwDKOhqTOUqxPAhuHQegIlTPsHa8eR8DILGMAAExOE4KaCd+MliVMEmQVJ+ZKrJBZmJwnjeH4gTQOw5IwAAMhA0RJAEaQZFkyl5BR1CetUdRNK0BjqAkaBaVhYxPBOSxmZGDFAp61xCaMXzRe8Zk8SCPmdio5QIM5mqwq2jLYmCpr3qRLJkhSw4Rb+TJkYUgFrphyWGKKEDijcdGGN2yFVTA5JgFUe6TrCaUcPVC6skuaEtTAE0itAXWSj2sqKWVVE+uqJ5ao2zatncLQjQsSw9aWmXKmmO17bqLZtcdo1nZlfHFIlfTbS4p63U293fo9p3pRGb1RoUMYYPGGlaZ9332r9h2GgD+5AyRDXrfK5aVtWvb1vtf1tkK5wwCdyMcOdfWVSWT6Digb4fl+BM-qj02oSuwGbhFswGTRSSapARHM-+s2chhoGGlzEE87BhEGYLZEYzlVHc0Z5PxZa5ROS57GcdxjEKQr70Rbpjz6ZLRnG7FIOluDYDlOpmm9NpjMW+JZt5hbBzoGTmBeL4-gBF4KDoI5zm+MwbnpJkmA22y-GVNIXIOVyG7NC0wWqKF3TK3m8lZWcCXqtntEvQreIQhsEA0LCRdoKVlFsmtpKDRSdOfjXU1C01c2izXIpigLjczaXipXTasN473quFJdMDbX6bvF3roNliUAlz-ywQQdASAAF4oBwwO+cvNuQwAjND13lD4m8ftve-LIW-WLsPhhY4+MBw82Ndy+tXflAnScQK9w6uKWWm0LpLzfjjD+E8F5oGdEvV6R8BJVnfhQEKiQYD1EoMMTUch96H2OIpE+akoaO3QRnTB2CoC4OCJkB+5lva+2sgESEzYHLQhgAAcUNKyCOHlo5eWYAbdUFQuHJ1TvYQ0Wc4G5zZDPb+JdsplxgMgWIPDRiqGrnAuu5YG5Pz7ENVu2jJJ5g7o1dkIteTUSMn3TqA8DFIMViZdeu1P42JglPfO6tZ6Xw8YvAuTj3rr2vgZO+BC5HEKEWfC+Y9QlbygLvfeZlB5OPBOQbGA13GQBzuAimaMm5DQ0WoWE5jf6WPQtYioOoECNB6FI0YJ5pCzAaSgM84tBqGj2HY8UrSOGxB-s-ZRuVKg1Lqa0ppLTDTtOwq0whXY1ZMWbmAYpqhtZcQQYEjaINyjVIQLU+phpJmdNGDMlAUzRiEP1mDaJpCHZ7IORM5pJy2kdLmYwyyftAjYB8FAbA3B4A024YaFIkdPI5GEdlPytQGiSOkTfQyeYUytLOVcvOCi4HIumV0zZlpY712pi+FAxSTGIvQBc15oxdHOPydNaqYBjHt0GTNP+lS+TAP7rLVJ2y9Gj3TG42Bpjg5eJnuvRRiCeWry9B9Px8Tb6JPvmi62tyEzn0diEhF4SGHcpflArJeMcm0TyRVApfZnz4JJSiw0ZTFystXDyEChzGnPKtbMrpGokh9OhMy1mbKvRPIpWcil3TNQvP6VgHVwyKxOpQMc115yXnzPotPSB-YabFPWbrLZIirgBpeUGxNkToy3Ptli51ga3k4q9p8lhlgUD7IgMkGAAApCAobimBB0AgUAIZBEQvxcgyoVRKSBRaK0mRQqwq9H+cAOtUA4AQHylAClTSlWLM9DXFMM650LqXSu6QAIJVRpMgAKzbWgElNdxzdp3Yu6A+7qV3hkAY+ljKdE+uFn6jl9iuWOMlSo1xP0v5wJgCK1NYrZElxzdKjVYSFURMjMqiFMT1Wys1fB7Vf7dUZPftklWm0CiDyvtgLQmRLVHOeduygu7oA2pZRU+aTyelhu9dy1l80qPzrvbAEBLGBmRpXuk9exSWg0fnakNtKgwMF3KAKu6ImxNwAk6IXFrpJXBOul9OTv0FPcaU5JsYa7i3IZgAmB2qY1RaaA8C0Yom9PKZUCkgxn7KRVGkOuTcrTmNev4851lrn3MgU42J1kvGfMRr8wx5O4QTxcjrJ5ij3nDTho-dh1B0CY2rukz4s9mpM2qeuSvRKTyjM3JM6WpImWD0fJ9lZf2XhZ1KSzLAYA2B-mEHiJg-hUcY7QYqAA5OXJU7GEiSmmTRh60UhQHAGpBW86bXKBwSbmQZv7PJiaulE3uCvjW2xipA2QIVAUA5ByzRQ2-spvRwCB3NxHZO2dpI3U9vXcToNyox3Tser41gatQA
