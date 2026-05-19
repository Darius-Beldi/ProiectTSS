# ProiectTSS
_Note: The project is docuemnted in the [wiki](https://github.com/Darius-Beldi/ProiectTSS/wiki) section of the repository_
# Description
This project is written in ```Java 26``` using Maven build tool. 


# Dependencies
The external libraries used, and imported through Maven are : 
- ```JUnit 5.1.0.2```, the framework used to run the tests
- ```Mockito 5.14.2```, the framework that allowed creating mocks of classes to bypass the database queries 
- ```Pitest 1.16.1```, the frameworkt that allows the mutation testing

# Sources used 
- [Junit Documentation ](https://junit.org/)
- [Maven Documentation](https://junit.org/)
- [Mockito Documentation](https://site.mockito.org/) 
- [Lecture Courses](https://drive.google.com/drive/folders/1EiuA624AToiUkGclPsycoJBkwlwuSvEN)



# Functional Testing 
## Function 1
```java
public static String Crypt(String input) throws NoSuchAlgorithmException 
```
### Equivalence partitioning classes

**Datele de intrare:** 
- un ```String```

**Clasele de echivalență:** 
 Clasă | Definiție | Tip |
|-------|-----------|-----|
| **C₁** | `{ input \| input este un String valid, nenul }` | Validă |
| **C₂** | `{ input \| input == null }` | Invalidă |

**Reprezentanții aleși:** 

 Test | Clasă | Valoare | Rezultat așteptat |
|------|-------|---------|-------------------|
| `testCrypt_ValidString` | C₁ | `"parola123"` | `"095b2626c9b6bad0eb89019ea6091bd9"` |
| `testCrypt_NullString` | C₂ | `null` | `NullPointerException` |
 




--- 
### Boundary Value Analysis

*Se testează aceeași funcție de hashing MD5, de data aceasta concentrându-ne asupra comportamentului la frontierele domeniului de intrare definit de lungimea șirului. Funcția trebuie să producă un hash valid de exact 32 de caractere pentru orice lungime a input-ului, inclusiv la valorile de frontieră: șirul vid (lungime 0), un singur caracter (lungime 1), o lungime tipică și o lungime foarte mare.*

**Datele de intrare:** 
- șirul de caractere de hashuit, cu len(input) ∈ [0, ∞)*


**Clase de echivalență identificate:**
 
| Clasă | Definiție |
|-------|-----------|
| **C₁** | `{ input \| input == "" }` — lungime 0 (frontieră inferioară) |
| **C₂** | `{ input \| \|input\| == 1 }` — lungime 1 (frontieră inferioară + 1) |
| **C₃** | `{ input \| 1 < \|input\| < 1000 }` — lungime tipică (interior clasă) |
| **C₄** | `{ input \| \|input\| == 1000 }` — lungime mare (frontieră superioară) |
 
**Valori de frontieră testate:**
 
| Test | Clasă | Valoare input | Rezultat așteptat |
|------|-------|---------------|-------------------|
| `testCrypt_EmptyString` | C₁ | `""` (lungime = 0) | `"d41d8cd98f00b204e9800998ecf8427e"`, length = 32 |
| `testCrypt_SingleChar` | C₂ | `"a"` (lungime = 1) | hash MD5 valid, length = 32 |
| `testCrypt_TypicalString` | C₃ | `"Password123!"` (lungime tipică) | hash MD5 valid, length = 32 |
| `testCrypt_LongString` | C₄ | `"aaa...a"` × 1000 (lungime = 1000) | hash MD5 valid, length = 32 |





# Function 2 : 
```java
Card(int id, int userId, String type, String cardName, String iban, String number, int month, int year, int cvv, int limit)
```
### Equivalence partitioning classes

*Se testează constructorul clasei `Card`, care creează un obiect reprezentând un card bancar asociat unui utilizator. Constructorul primește 10 parametri ce descriu identitatea, datele și limitele cardului. Un card valid trebuie să respecte constrângerile fiecărui parametru în parte; dacă oricare dintre parametri se află în afara domeniului valid, obiectul nu poate fi creat și constructorul aruncă `IllegalArgumentException`.*

Există 10 intrări:

- `id` — identificatorul unic al cardului (`int`)
- `userId` — identificatorul utilizatorului proprietar (`int`)
- `type` — tipul cardului, ex. `"Debit"`, `"Credit"` (`String`)
- `cardName` — numele afișat al cardului (`String`)
- `iban` — codul IBAN asociat contului (`String`)
- `number` — numărul cardului, format din exact 16 cifre (`String`)
- `month` — luna de expirare, în intervalul `[1..12]` (`int`)
- `year` — anul de expirare, valoare pozitivă (`int`)
- `cvv` — codul de securitate de 3 cifre, în intervalul `[100..999]` (`int`)
- `limit` — limita de cheltuieli, valoare non-negativă (`int`)

---

## 1. Domeniu de intrări — clase de echivalență individuale

### `id` — identificator unic al cardului

| Clasă | Definiție | Tip |
|-------|-----------|-----|
| **ID₁** | `{ id \| id > 0 }` | Validă |
| **ID₂** | `{ id \| id ≤ 0 }` | Invalidă |

### `userId` — identificator utilizator

| Clasă | Definiție | Tip |
|-------|-----------|-----|
| **UID₁** | `{ userId \| userId > 0 }` | Validă |
| **UID₂** | `{ userId \| userId ≤ 0 }` | Invalidă |

### `type` — tipul cardului

| Clasă | Definiție | Tip |
|-------|-----------|-----|
| **T₁** | `{ type \| type ≠ null ∧ type ≠ "" }` | Validă |
| **T₂** | `{ type \| type = null }` | Invalidă |
| **T₃** | `{ type \| type = "" }` | Invalidă |

### `cardName` — numele afișat al cardului

| Clasă | Definiție | Tip |
|-------|-----------|-----|
| **CN₁** | `{ cardName \| cardName ≠ null ∧ cardName ≠ "" }` | Validă |
| **CN₂** | `{ cardName \| cardName = null }` | Invalidă |
| **CN₃** | `{ cardName \| cardName = "" }` | Invalidă |

### `iban` — codul IBAN

| Clasă | Definiție | Tip |
|-------|-----------|-----|
| **IB₁** | `{ iban \| iban ≠ null ∧ iban ≠ "" }` | Validă |
| **IB₂** | `{ iban \| iban = null }` | Invalidă |
| **IB₃** | `{ iban \| iban = "" }` | Invalidă |

### `number` — numărul cardului

| Clasă | Definiție | Tip |
|-------|-----------|-----|
| **NR₁** | `{ number \| number ≠ null ∧ \|number\| = 16 }` | Validă |
| **NR₂** | `{ number \| number = null }` | Invalidă |
| **NR₃** | `{ number \| number ≠ null ∧ \|number\| ≠ 16 }` | Invalidă |

### `month` — luna de expirare

| Clasă | Definiție | Tip |
|-------|-----------|-----|
| **M₁** | `{ month \| 1 ≤ month ≤ 12 }` | Validă |
| **M₂** | `{ month \| month < 1 }` | Invalidă |
| **M₃** | `{ month \| month > 12 }` | Invalidă |

### `year` — anul de expirare

| Clasă | Definiție | Tip |
|-------|-----------|-----|
| **Y₁** | `{ year \| year ≥ 0 }` | Validă |
| **Y₂** | `{ year \| year < 0 }` | Invalidă |

### `cvv` — codul de securitate

| Clasă | Definiție | Tip |
|-------|-----------|-----|
| **CVV₁** | `{ cvv \| 100 ≤ cvv ≤ 999 }` | Validă |
| **CVV₂** | `{ cvv \| cvv < 100 }` | Invalidă |
| **CVV₃** | `{ cvv \| cvv > 999 }` | Invalidă |

### `limit` — limita de cheltuieli

| Clasă | Definiție | Tip |
|-------|-----------|-----|
| **LIM₁** | `{ limit \| limit ≥ 0 }` | Validă |
| **LIM₂** | `{ limit \| limit < 0 }` | Invalidă |

---

## 2. Domeniu de ieșiri

Constructorul produce două tipuri de rezultate:

- **Obiect `Card` valid** — toți parametrii respectă constrângerile
- **`IllegalArgumentException`** — cel puțin un parametru se află în clasa invalidă


## 3. Clase de echivalență globale

Clasele globale se obțin combinând clasele individuale, cu constrângerea că se variază câte un singur parametru invalid pe rând (restul rămân în clasa validă). Fără constrângeri ar rezulta `2×2×3×3×3×3×3×2×3×2 = 3888` combinații; cu constrângerile aplicate se reduc la **17 clase**.

| Clasă globală | Combinație | Reprezentant `(id, userId, type, cardName, iban, number, month, year, cvv, limit)` | Rezultat așteptat |
|---------------|------------|------------------------------------------------------------------------------------|-------------------|
| **C_valid** | ID₁ UID₁ T₁ CN₁ IB₁ NR₁ M₁ Y₁ CVV₁ LIM₁ | `(1, 1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 12, 25, 123, 200)` | Card creat corect |
| **C_id** | **ID₂** UID₁ T₁ CN₁ IB₁ NR₁ M₁ Y₁ CVV₁ LIM₁ | `(0, 1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 12, 25, 123, 200)` | `IllegalArgumentException` |
| **C_uid** | ID₁ **UID₂** T₁ CN₁ IB₁ NR₁ M₁ Y₁ CVV₁ LIM₁ | `(1, -1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 12, 25, 123, 200)` | `IllegalArgumentException` |
| **C_tnull** | ID₁ UID₁ **T₂** CN₁ IB₁ NR₁ M₁ Y₁ CVV₁ LIM₁ | `(1, 1, null, "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 12, 25, 123, 200)` | `IllegalArgumentException` |
| **C_tempty** | ID₁ UID₁ **T₃** CN₁ IB₁ NR₁ M₁ Y₁ CVV₁ LIM₁ | `(1, 1, "", "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 12, 25, 123, 200)` | `IllegalArgumentException` |
| **C_cnnull** | ID₁ UID₁ T₁ **CN₂** IB₁ NR₁ M₁ Y₁ CVV₁ LIM₁ | `(1, 1, "Debit", null, "RO49AAAA1B31007593840000", "1234567890123456", 12, 25, 123, 200)` | `IllegalArgumentException` |
| **C_cnempty** | ID₁ UID₁ T₁ **CN₃** IB₁ NR₁ M₁ Y₁ CVV₁ LIM₁ | `(1, 1, "Debit", "", "RO49AAAA1B31007593840000", "1234567890123456", 12, 25, 123, 200)` | `IllegalArgumentException` |
| **C_ibnull** | ID₁ UID₁ T₁ CN₁ **IB₂** NR₁ M₁ Y₁ CVV₁ LIM₁ | `(1, 1, "Debit", "Debit Card", null, "1234567890123456", 12, 25, 123, 200)` | `IllegalArgumentException` |
| **C_ibempty** | ID₁ UID₁ T₁ CN₁ **IB₃** NR₁ M₁ Y₁ CVV₁ LIM₁ | `(1, 1, "Debit", "Debit Card", "", "1234567890123456", 12, 25, 123, 200)` | `IllegalArgumentException` |
| **C_nrnull** | ID₁ UID₁ T₁ CN₁ IB₁ **NR₂** M₁ Y₁ CVV₁ LIM₁ | `(1, 1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", null, 12, 25, 123, 200)` | `IllegalArgumentException` |
| **C_nrlen** | ID₁ UID₁ T₁ CN₁ IB₁ **NR₃** M₁ Y₁ CVV₁ LIM₁ | `(1, 1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", "1234567890", 12, 25, 123, 200)` | `IllegalArgumentException` |
| **C_mlow** | ID₁ UID₁ T₁ CN₁ IB₁ NR₁ **M₂** Y₁ CVV₁ LIM₁ | `(1, 1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 0, 25, 123, 200)` | `IllegalArgumentException` |
| **C_mhigh** | ID₁ UID₁ T₁ CN₁ IB₁ NR₁ **M₃** Y₁ CVV₁ LIM₁ | `(1, 1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 13, 25, 123, 200)` | `IllegalArgumentException` |
| **C_yneg** | ID₁ UID₁ T₁ CN₁ IB₁ NR₁ M₁ **Y₂** CVV₁ LIM₁ | `(1, 1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 12, -1, 123, 200)` | `IllegalArgumentException` |
| **C_cvvlow** | ID₁ UID₁ T₁ CN₁ IB₁ NR₁ M₁ Y₁ **CVV₂** LIM₁ | `(1, 1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 12, 25, 99, 200)` | `IllegalArgumentException` |
| **C_cvvhigh** | ID₁ UID₁ T₁ CN₁ IB₁ NR₁ M₁ Y₁ **CVV₃** LIM₁ | `(1, 1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 12, 25, 1000, 200)` | `IllegalArgumentException` |
| **C_limneg** | ID₁ UID₁ T₁ CN₁ IB₁ NR₁ M₁ Y₁ CVV₁ **LIM₂** | `(1, 1, "Debit", "Debit Card", "RO49AAAA1B31007593840000", "1234567890123456", 12, 25, 123, -1)` | `IllegalArgumentException` |

**17 clase → 17 reprezentanți/teste**


## 4. Setul de date de test

| Test | Clasă | Parametru variat | Valoare | Rezultat așteptat |
|------|-------|-----------------|---------|-------------------|
| `testCard_Constructor_Valid` | C_valid | — | toți valizi | Card creat, toate câmpurile corecte |
| `testCard_Constructor_InvalidId` | C_id | `id` | `0` | `IllegalArgumentException` |
| `testCard_Constructor_InvalidUserId` | C_uid | `userId` | `-1` | `IllegalArgumentException` |
| `testCard_Constructor_NullType` | C_tnull | `type` | `null` | `IllegalArgumentException` |
| `testCard_Constructor_EmptyType` | C_tempty | `type` | `""` | `IllegalArgumentException` |
| `testCard_Constructor_NullCardName` | C_cnnull | `cardName` | `null` | `IllegalArgumentException` |
| `testCard_Constructor_EmptyCardName` | C_cnempty | `cardName` | `""` | `IllegalArgumentException` |
| `testCard_Constructor_NullIban` | C_ibnull | `iban` | `null` | `IllegalArgumentException` |
| `testCard_Constructor_EmptyIban` | C_ibempty | `iban` | `""` | `IllegalArgumentException` |
| `testCard_Constructor_NullNumber` | C_nrnull | `number` | `null` | `IllegalArgumentException` |
| `testCard_Constructor_InvalidNumberLength` | C_nrlen | `number` | `"1234567890"` (10 cifre) | `IllegalArgumentException` |
| `testCard_Constructor_MonthTooLow` | C_mlow | `month` | `0` | `IllegalArgumentException` |
| `testCard_Constructor_MonthTooHigh` | C_mhigh | `month` | `13` | `IllegalArgumentException` |
| `testCard_Constructor_NegativeYear` | C_yneg | `year` | `-1` | `IllegalArgumentException` |
| `testCard_Constructor_CvvTooLow` | C_cvvlow | `cvv` | `99` | `IllegalArgumentException` |
| `testCard_Constructor_CvvTooHigh` | C_cvvhigh | `cvv` | `1000` | `IllegalArgumentException` |
| `testCard_Constructor_NegativeLimit` | C_limneg | `limit` | `-1.0` | `IllegalArgumentException` |


# Boundary Value Analysis

*Se testează același constructor al clasei `Card`, de data aceasta concentrându-ne asupra **valorilor de la granița dintre clasele de echivalență**, care reprezintă surse frecvente de erori. BVA se aplică exclusiv pe parametrii cu domeniu numeric mărginit sau cu o constrângere de lungime exactă. Parametrii `String` fără domeniu dimensional explicit (`type`, `cardName`, `iban`) nu au frontiere numerice și au fost acoperiți complet prin EP.*

Parametrii analizați și frontierele lor:

- `month` — interval bilateral `[1..12]`
- `cvv` — interval bilateral `[100..999]`
- `number` — lungime exactă `|number| = 16`
- `id` — mărginit inferior, minim `1`
- `userId` — mărginit inferior, minim `1`
- `year` — mărginit inferior, minim `0`
- `limit` — mărginit inferior, minim `0.0`


## 1. Valori de frontieră identificate per parametru

### `month` — interval `[1..12]`

Clasele de echivalență (din EP): M₁ = `[1..12]`, M₂ = `< 1`, M₃ = `> 12`

| Frontieră | Valoare | Clasă | Tip |
|-----------|---------|-------|-----|
| inferioară − 1 | `0` | M₂ | Invalidă |
| inferioară (minim) | `1` | M₁ | Validă |
| inferioară + 1 | `2` | M₁ | Validă |
| superioară − 1 | `11` | M₁ | Validă |
| superioară (maxim) | `12` | M₁ | Validă |
| superioară + 1 | `13` | M₃ | Invalidă |

### `cvv` — interval `[100..999]`

Clasele de echivalență (din EP): CVV₁ = `[100..999]`, CVV₂ = `< 100`, CVV₃ = `> 999`

| Frontieră | Valoare | Clasă | Tip |
|-----------|---------|-------|-----|
| inferioară − 1 | `99` | CVV₂ | Invalidă |
| inferioară (minim) | `100` | CVV₁ | Validă |
| inferioară + 1 | `101` | CVV₁ | Validă |
| superioară − 1 | `998` | CVV₁ | Validă |
| superioară (maxim) | `999` | CVV₁ | Validă |
| superioară + 1 | `1000` | CVV₃ | Invalidă |

### `number` — lungime exactă `16`

Clasele de echivalență (din EP): NR₁ = `|number| = 16`, NR₃ = `|number| ≠ 16`

| Frontieră | Valoare `\|number\|` | Clasă | Tip |
|-----------|---------------------|-------|-----|
| exactă − 1 | `15` | NR₃ | Invalidă |
| exactă | `16` | NR₁ | Validă |
| exactă + 1 | `17` | NR₃ | Invalidă |

### `id` — mărginit inferior, minim `1`

Clasele de echivalență (din EP): ID₁ = `> 0`, ID₂ = `≤ 0`

| Frontieră | Valoare | Clasă | Tip |
|-----------|---------|-------|-----|
| inferioară − 1 | `0` | ID₂ | Invalidă |
| inferioară (minim) | `1` | ID₁ | Validă |
| inferioară + 1 | `2` | ID₁ | Validă |

### `userId` — mărginit inferior, minim `1`

Clasele de echivalență (din EP): UID₁ = `> 0`, UID₂ = `≤ 0`

| Frontieră | Valoare | Clasă | Tip |
|-----------|---------|-------|-----|
| inferioară − 1 | `0` | UID₂ | Invalidă |
| inferioară (minim) | `1` | UID₁ | Validă |
| inferioară + 1 | `2` | UID₁ | Validă |

### `year` — mărginit inferior, minim `0`

Clasele de echivalență (din EP): Y₁ = `≥ 0`, Y₂ = `< 0`

| Frontieră | Valoare | Clasă | Tip |
|-----------|---------|-------|-----|
| inferioară − 1 | `-1` | Y₂ | Invalidă |
| inferioară (minim) | `0` | Y₁ | Validă |
| inferioară + 1 | `1` | Y₁ | Validă |

### `limit` — mărginit inferior, minim `0.0`

Clasele de echivalență (din EP): LIM₁ = `≥ 0`, LIM₂ = `< 0`

| Frontieră | Valoare | Clasă | Tip |
|-----------|---------|-------|-----|
| inferioară − ε | `-0.01` | LIM₂ | Invalidă |
| inferioară (minim) | `0.0` | LIM₁ | Validă |
| inferioară + ε | `0.01` | LIM₁ | Validă |

## 2. Setul de date de test

Ceilalți parametri (cei care nu sunt variați) se fixează la valorile reprezentantului valid din EP: `id=1, userId=1, type="Debit", cardName="Debit Card", iban="RO49AAAA1B31007593840000", number="1234567890123456", month=6, year=25, cvv=500, limit=200`.

| Test | Parametru | Valoare | Frontieră | Rezultat așteptat |
|------|-----------|---------|-----------|-------------------|
| `testCard_Month_BelowMin` | `month` | `0` | inf − 1 | `IllegalArgumentException` |
| `testCard_Month_AtMin` | `month` | `1` | inf (minim) | `month == 1` |
| `testCard_Month_AboveMin` | `month` | `2` | inf + 1 | `month == 2` |
| `testCard_Month_BelowMax` | `month` | `11` | sup − 1 | `month == 11` |
| `testCard_Month_AtMax` | `month` | `12` | sup (maxim) | `month == 12` |
| `testCard_Month_AboveMax` | `month` | `13` | sup + 1 | `IllegalArgumentException` |
| `testCard_Cvv_BelowMin` | `cvv` | `99` | inf − 1 | `IllegalArgumentException` |
| `testCard_Cvv_AtMin` | `cvv` | `100` | inf (minim) | `cvv == 100` |
| `testCard_Cvv_AboveMin` | `cvv` | `101` | inf + 1 | `cvv == 101` |
| `testCard_Cvv_BelowMax` | `cvv` | `998` | sup − 1 | `cvv == 998` |
| `testCard_Cvv_AtMax` | `cvv` | `999` | sup (maxim) | `cvv == 999` |
| `testCard_Cvv_AboveMax` | `cvv` | `1000` | sup + 1 | `IllegalArgumentException` |
| `testCard_Number_TooShort` | `number` | lungime `15` | exactă − 1 | `IllegalArgumentException` |
| `testCard_Number_ExactLength` | `number` | lungime `16` | exactă | `number == "1234567890123456"` |
| `testCard_Number_TooLong` | `number` | lungime `17` | exactă + 1 | `IllegalArgumentException` |
| `testCard_Id_BelowMin` | `id` | `0` | inf − 1 | `IllegalArgumentException` |
| `testCard_Id_AtMin` | `id` | `1` | inf (minim) | `id == 1` |
| `testCard_Id_AboveMin` | `id` | `2` | inf + 1 | `id == 2` |
| `testCard_UserId_BelowMin` | `userId` | `0` | inf − 1 | `IllegalArgumentException` |
| `testCard_UserId_AtMin` | `userId` | `1` | inf (minim) | `userId == 1` |
| `testCard_UserId_AboveMin` | `userId` | `2` | inf + 1 | `userId == 2` |
| `testCard_Year_BelowMin` | `year` | `-1` | inf − 1 | `IllegalArgumentException` |
| `testCard_Year_AtMin` | `year` | `0` | inf (minim) | `year == 0` |
| `testCard_Year_AboveMin` | `year` | `1` | inf + 1 | `year == 1` |
| `testCard_Limit_BelowMin` | `limit` | `-0.01` | inf − ε | `IllegalArgumentException` |
| `testCard_Limit_AtMin` | `limit` | `0.0` | inf (minim) | `limit == 0.0` |
| `testCard_Limit_AboveMin` | `limit` | `0.01` | inf + ε | `limit == 0.01` |

**27 valori de frontieră → 27 teste**



# Structural Testing



# Function to test : AuthenticationService.Login(String email, String password) 
A function that tests if the input email and password are a valid combination for a user 

### Function Code

<img width="1017" height="583" alt="image" src="https://github.com/user-attachments/assets/a564804d-6b8e-4c36-869c-cfb67ac434c6" />


### Control Flow Graph
<img width="300" height="1450" alt="ControlFlowDiagram" src="https://github.com/user-attachments/assets/a9183892-f97f-4953-8286-661e33b05578" />


For coverage testing we used the ```Mokito``` library to make mocks to simulate database entries. 

```java
    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private AuditService auditService;
    @Mock
    private PreparedStatement checkForExistingEmailStatement;
    @Mock
    private PreparedStatement getPasswordStatement;
    @Mock
    private PreparedStatement getUserStatement;
    @Mock
    private ResultSet rsEmail;
    @Mock
    private ResultSet rsPassword;
    @Mock
    private ResultSet rsUser;
```

## 1) Instruction Coverage 
```testLoginSuccess()```  tests almost all the lines (doesn't make sense to test for the else, because they all lead to ```return null```)
```java
public void testLoginSuccess() throws SQLException, NoSuchAlgorithmException {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String hashedPassword = AuthenticationService.Crypt(password);
        Date birthDate = new Date();

        when(checkForExistingEmailStatement.executeQuery()).thenReturn(rsEmail);
        when(rsEmail.next()).thenReturn(true);
        when(rsEmail.getString(1)).thenReturn(email);

        when(getPasswordStatement.executeQuery()).thenReturn(rsPassword);
        when(rsPassword.next()).thenReturn(true);
        when(rsPassword.getString(1)).thenReturn(hashedPassword);

        when(getUserStatement.executeQuery()).thenReturn(rsUser);
        when(rsUser.next()).thenReturn(true);
        when(rsUser.getInt(1)).thenReturn(1);
        when(rsUser.getString(2)).thenReturn("John");
        when(rsUser.getString(3)).thenReturn("Doe");
        when(rsUser.getDate(4)).thenReturn(new java.sql.Date(birthDate.getTime()));
        when(rsUser.getString(5)).thenReturn(email);
        when(rsUser.getString(6)).thenReturn(hashedPassword);

        // Act
        User user = authenticationService.login(email, password);

        // Assert
        assertNotNull(user);
        assertEquals(1, user.getIdUser());
        assertEquals("John", user.getFirstName());
        verify(auditService, times(1)).logAction("Logged in successfully for user: 1");
    }
```


## 2) Decision Coverage 
The tests : 
- ```testLoginEmailNotFound()```
- ```testLoginWrongPassword()```
- ```testLoginUserNotFoundAfterPasswordMatch()```
- ```testLoginPasswordResultNotAvailable()```

All those test for when the if clauses are ```false``` and go to line 30, ```return  null```


## 3) Conditional Coverage 
This function checks if the User object has any of the string fields ```null```
```java 
 public boolean isNull(){
        if (FirstName == null || LastName == null || BirthDate == null || Email == null || Password == null)
            return true;
        else
            return false;
    }
```

The conditional coverage tests are the following : 
- ```testIsNullAllFieldsPresent()```
- ```testIsNullFirstNameNull()```
- ```testIsNullLastNameNull()```
- ```testIsNullBirthDateNull()```
- ```testIsNullEmailNull()```
- ```testIsNullPasswordNull()```
