# Changelog

## 3.1 (released 21.01.2025)

- changed lazy methods access modifier to public
- removed unnecessary JKScope.main method
- added lazyOf methods test
- added new recur methods tests
- added null checks for lazy methods args

## 3.0 (released 20.01.2025)

- big refactoring, no backward compatibility

## 2.3 (released 10.03.2024)

- added `Lazy` object and `JKScope` methods
- replaced `let` of value method with `opt` method
- replaced `asUnchecked` functional interface method with `unchecked` static method due to implementation problems

## 2.2 (released 25.02.2024)

- renamed `let` interface method to `letIt` (due to the same method name as a static method)
- added `letIntWith`, `letLongWith`, `letDoubleWith` static methods

## 2.1 (released 18.02.2024)

- added `Opt.throwIfEmpty` method
- added `JKScope.withResource` and `JKScope.letWithResource` static methods

## 2.0 (released 17.02.2024)

Big refactoring. Stable API.

## 1.0 (released 11.02.2024)

First release
