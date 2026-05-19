# Mutation Testing

Pentru testarea cu mutanti am folosit framework-ul ```PIT``` (PITest), integrat prin Maven.

```xml
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.16.1</version>
    <dependencies>
        <dependency>
            <groupId>org.pitest</groupId>
            <artifactId>pitest-junit5-plugin</artifactId>
            <version>1.2.1</version>
        </dependency>
    </dependencies>
    <configuration>
        <targetClasses><param>Models.UserValidator</param></targetClasses>
        <targetTests><param>MutationTesting</param></targetTests>
        <mutators><mutator>STRONGER</mutator></mutators>
    </configuration>
</plugin>
```

Comanda de rulare: ```mvn clean test-compile org.pitest:pitest-maven:mutationCoverage```. Raportul HTML este generat in ```target/pit-reports/index.html```.

## Functia testata : UserValidator.isNull(...)

```java
public static boolean isNull(String firstName, String lastName, Date birthDate,
                             String email, String password) {
    if (firstName == null || lastName == null || birthDate == null
            || email == null || password == null)
        return true;
    else
        return false;
}
```

Functia a fost extrasa intr-o clasa separata ```UserValidator``` pentru a izola logica pura de initializatorul static al clasei ```User``` (care depinde de baza de date).

## Operatori de mutatie aplicati

Din setul ```STRONGER```, pe aceasta functie sunt relevanti:
- ```REMOVE_CONDITIONALS_EQUAL_IF``` — inlocuieste fiecare ```== null``` cu ```true```
- ```REMOVE_CONDITIONALS_EQUAL_ELSE``` — inlocuieste fiecare ```== null``` cu ```false```
- ```BOOLEAN_TRUE_RETURNS``` — ```return true``` devine ```return false```
- ```BOOLEAN_FALSE_RETURNS``` — ```return false``` devine ```return true```

## Set initial de teste

```java
@Test
public void testAllFieldsPresent_returnsFalse() {
    assertFalse(UserValidator.isNull("John", "Doe", new Date(), "a@b.com", "pass"));
}

@Test
public void testFirstNameNull_returnsTrue() {
    assertTrue(UserValidator.isNull(null, "Doe", new Date(), "a@b.com", "pass"));
}

@Test
public void testPasswordNull_returnsTrue() {
    assertTrue(UserValidator.isNull("John", "Doe", new Date(), "a@b.com", null));
}
```

## Raport PIT initial

| Metrica | Valoare |
| --- | --- |
| Mutanti generati | 12 |
| Omorati | 9 |
| Supravietuitori | 3 |
| Mutation Score | 75% |

Cei 3 mutanti supravietuitori inlocuiesc ```== null``` cu ```true``` pe campurile ```firstName```, ```lastName``` si ```email```.

## Analiza a 2 mutanti supravietuitori

### M_lastName : ```lastName == null``` → ```true```

Programul mutant:
```java
if (firstName == null || true || birthDate == null || email == null || password == null)
```

Input distinctiv: toate campurile non-null — ```isNull("John", "Doe", new Date(), "a@b.com", "pass")```
- P  : ```false || false || false || false || false``` = ```false```
- M  : ```false || true || ...``` = ```true```

Iesirile difera, deci mutantul **nu este echivalent**.

### M_email : ```email == null``` → ```true```

Programul mutant:
```java
if (firstName == null || lastName == null || birthDate == null || true || password == null)
```

Input distinctiv: toate campurile non-null — ```isNull("John", "Doe", new Date(), "a@b.com", "pass")```
- P  : ```false || false || false || false || false``` = ```false```
- M  : ```false || false || false || true || ...``` = ```true```

Iesirile difera, deci mutantul **nu este echivalent**.

## Teste suplimentare

Cele 2 teste izoleaza fiecare camp ca singura valoare null, fortand evaluarea fiecarei conditii din lantul ```||```:

```java
@Test
public void testOnlyLastNameNull_killsLastNameMutant() {
    assertTrue(UserValidator.isNull("John", null, new Date(), "a@b.com", "pass"));
}

@Test
public void testOnlyEmailNull_killsEmailMutant() {
    assertTrue(UserValidator.isNull("John", "Doe", new Date(), null, "pass"));
}
```

## Raport PIT final

| Metrica | Inainte | Dupa |
| --- | --- | --- |
| Mutanti generati | 12 | 12 |
| Omorati | 9 | **11** |
| Supravietuitori | 3 | **1** |
| Mutation Score | 75% | **92%** |
