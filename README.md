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
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5xDAaTgALdvYoALIoAIye-Ezw-g7BKABMEZI+MYEhAMxJ3sx+AXEALFlRubEhAKxFUiVpKABslSl5IQDsDTmpcQAcbdFNKB68XsUdIaFOPdVxoeH2UBAArtgwAMRowFSWMABKKPZIqhZySBBoKwDuvkhgYoiopAC0AHzklDRQAFwwANoACgDyZAAKgBdGAAenmBigAB00ABvABEkMoawAtigEe8ETAEQAaHG4dRnaAcTHYvE4lCo4BIBBknEAX0wwjeMCerHYXEon0RyKgaIxWJx+IRhNUxKgpKFFIRVJpdOlTLYnG4sHZLNEnygu321ygAAodnsDpQdgBHeZqMAASmZr1EbOesnkShU6k+gTAAFUofq+QLbc7FMo1KpHUYdJ8tgBRBTSGD+4DomAAMzmqITUMwQddobZ6vtKhgn2jUDmHxgSDQVGAyA4PpRScMdpERfu7OVXIrpfLnyrNbrDYFmE7qvzzxZ3JgiJ70E+te1wA4m3kAGt0EzJ2r2bdzJ98k50rCZ2W5zAFyglyvgOu0AimTmQ+pxy9WyhPmR5k-VJ8AGq1pAOBgBt+SbFsqAddlHzdH8YBARdrhAv0oQFfExQlDh8TlWlA20XNn3ZYxPjgGMFEBaNM0bdE0P0cUSSw6laRgABJAA5QE-koqBwMgp08O-T54Mva4FHmMBfGQqiUFwl1v3DIj4FI8iuNQ88xN8QEIFvFj2M44B1OzfiYPkyNtljeNE2otTxM07S2I46zfEM2TjPbCdC3fRzbPQHi2w7TlVSjHUTSgHZVHmBAsFHSgXy3HkkRQptsU+ckRX0mytPQZKcUZcCYrc+BkD3GB4icJxjwSqTstSnF0o0zK72LHL7zMThTFmBYlmWaA8hgAAZCA9lOZYLiuG4ioedzqCnX4ARBcEDHUY40AqyyMRFdCSRard-JVKdeUS9EZU2yUWuitUpoglRPgQQaq31ECg1tDU2z4lzQw9FBvV9NaZODVzngU0iLMOww0wgDM+V8wwoKMj6YE9H5aIw-UTo4P78LDQjTOBmA0dTaBIazaC8wKl7PNnCtmOrACgJAgUwSRokSWhl9zpLU8qZputgNBxnkZZ87YtefaEUplwWO5wDeak-nmdOzcRe3Z5dwwfcyoq8XJYHaX6abOW6IV5z-tJgs3w-L8YL-WmZdA5NORgJnDY4VnYfe59BIQlBRPEyS7ek43MZM4ilIotb8Tq7zTirSBHMDuTsajcyVKbCP1KjnSHLq+PXLNq7PMjhrXd2rtPgGoawoiqKAvyvPRbW6rhVq9OGsb7alZfVWwE+UryvhSr-bbtKW9vNumXQF3MA6xYVlmFB0H6waFmYEbLmuTAu-DOLvmkaM+ujcjQTBRbVGW2FC9vPKLo5PaKwvnyhbzh0tSpCAaH1e+0GejzwxJ90Ea+j7CSn8MYJ0BjjZOn9Uzpjjn-LGT8iwc17NrG2UDi7PHZjASm84R4PxrsrV8bx4rYITGsdS0AkAAC8UAcEVtNAhXd1ahE1pzT48wyHiQodQ0kD44YEQQYYC2AkYA6yAp-HOpNwGfF3vvZSUDwYZmzuTNm+ChFWxEag3BaARz4OFvQ+Kn5hEUCWicGAgJKCoirHIGhdDWQFUYSVDW-djGn1MeYqAli1jXB4a1Se08uraiAn1XUMAADiTYwyrzGhvCazA64Vi+KEg+R97BNnPloq+4ZMHiMfpdZ+MBkAHHCeiVQH8tHfzfL-PhsFPRALKRlW8oCAYRiTnGLyDVoEQ1gdUvR+cmokOpqI9p2l0E31Llg1hwy8G316aLEh7C6pcJsZk+xsSmEsOQQs8hUAqE0JanA3pmpyCW3hkMyAl9lFuxNv-T0xS1D6iaZIlpZk2lfFrAgYEcJUnomYtIfE3yUCsVTgjJsDJOkZgBcEg4Ej+F5Out8d5nyAW-P+U2IFVkAW2MgiXQKACwB3NUJXSKOiZlkyVp8N5CAPlfKbCikF6J0UoFReiWxtcVZrMcX3Sl1LkV-PpYC4FmLfHtTmDPZY2B5hQGwNweAXswlNnOGvcadw4mXRmv8IEKS0kcPqreY8ALGWsuvtkrR+q0WgpJV2LeP9PbCRQHc+pur0DMoFeiCpfSrmY0+mAOpICYVYykS8+M8iYHZwOWSypSCzyDM0Q0heozMEkJyboiNRDpxi0mVszhOzuFGs7hyg8zD+7zJ1Usnx4aBFqNOTbc5PlLlvWubBIS1iHUGqbI8gigaSKxmUjSn5fK20YtBZWU4kLdT+uDkG6cvKXWMpdWCqs-KoVYArXC98faUB0sHUy-lWK-IYNUXBL2dyiXV1JfEnkM7+Vzt3SsncHLe5mv7bOwVFqJ4is6isSwKAqUQDODAAAUhARddyVg6AQKAVcMSVXWv0d8H4Xp5pggBekuNK14TSuAN+qAcAIC3SgC635eacVTk-seTD2HcP4cI9IJUKa11NQAFbAbQA6z+aEIOUbw9AGj7reIyGqd6315SJ2JynSGrpYaempvyQMqWYitEwATYepNGTH4XvTSWxZObllbnzSq9ZxbM2lp0+W6TlbjnCLOUXcmTw4FsOwFoa4rbaV8oo5QKj0AO0BuebjXl4Kl3jvDYG3G7mcPcdgAowL0LV2EKOSQu5YJPM4d8MBlQynb6fBjTzRLyW-BpbEOptVFYTzlgltl6WuWIv5dEO3eh+nioHj7qV6A5X5PyvREl6rqXau8PdqoMTXofjSDIhRAFAWx0xeqYN4bo28acY8xFsMUXJsrumyFg+WxmLRl-GN1zE2mzLtExZwx6iN1EYy+M5jVZT2WrHKm-avLiP3oM5y2E07XPjzalPUVXVZhYcKpeWAwBsDSsIEcUxUT16bw018GRB9oxH2MCsg9mWjA-q+igOA7y7v5TXZ8DgGPrjY6pa7BtXr0fcBEqT4Lzz4fKS+AoPqfVQSLqk-1yd9OKKM+Z6z04UNacKS598JnLOR3RawO+oAA
