package s03_streamApi.t04_terminalOperation;
// max/mix(Comparator compare)
// .max(Comparator.naturalOrder() - ищет макс число (естественный порядок)
// .min(Comparator.reverseOrder() - ищет минимальное число (обратный порядок)

// Сценарий 2: У нас поток сложных объектов (Например, Stream<User>). Здесь naturalOrder не сработает (Java не знает, кто больше: Вася или Петя).
// .max(Comparator.comparing(User::getAge)) - Найти макс сравнивая пользователей по их возрасту
// .max(Comparator.comparing(User::getAge).thenComparing(User::getName)) -
// Optional<Integer> maxSalary = salaries.stream().max(Integer::compare); int res = maxSalary.orElse(0) - safety

//----------

// anyMatch(Predicate)
// boolean hasEven = stream.anyMatch(n -> n % 2 == 0); boolean allPositive = stream.allMatch(n -> n > 0);

//----------

// reduce(BinaryOperation accumulator)
// List<Integer> numbers = Arrays.asList(1,2,3,4,5); int sum = numbers.stream().reduce(0,(a,b) -> a + b);
// 0 - start, a - накопленный рез, b - след эл конвейера

//----------



