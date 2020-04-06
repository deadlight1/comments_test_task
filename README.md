# comments_test_task
How to run without docker:
git clone https://github.com/deadlight1/comments_test_task.git
maven: clean, install

With docker:
docker pull deadlight1/test-task:second

# Задача
Имеется класс, реализующий два метода, реализующих не очень быстрый и не самый надежный функционал.
Метод doSomeWorkOnCommentCreation() имитирует какую-то бизнес логику при добавления комментария в системе.
Метод doSomeWorkOnNotification() имитирует какую-то бизнес логику при добавлении уведомления в системе.
```java
public class BusinessLogic {
	public static void sleepAndRandomThrowRuntimeException(int seconds, int exceptionProbabilityProc){
		try {
			Thread.sleep((long) seconds * 1000 * Math.random());
		} catch (InterruptedException e) {}
		int randomProc = (int) (100 * Math.random());
		if(exceptionProbabilityProc > randomProc) throw new RuntimeException();
	}
	public static void doSomeWorkOnNotification(){
		sleepAndRandomThrowRuntimeException(2, 10);
	}
	public static void doSomeWorkOnCommentCreation(){
		sleepAndRandomThrowRuntimeException(1, 30);
	}
}
```
Задача:

Применяя возможности Spring написать REST API добавления комментариев в базу.
База содержит 2 таблицы:
 1. Комментарии (id, comment, time)
 2. Уведомления о добавленных комментариях (id, comment_id, time, boolean delivered)
 
API позволяет:
 1. Добавить комментарий
 2. Получить список добавленных комментариев с пагинацией по 10 записей
 3. Получить список созданных уведомлений с пагинацией по 10 записей
 
Уведомление о комментарии в системе создается автоматически только после успешного сохранения комментария в базу и ПОСЛЕ ЭТОГО отработки метода doSomeWorkOnCommentCreation().
При этом, при неудачной отработке метода doSomeWorkOnCommentCreation() комментарий должен быть удален из базы данных.

Процесс создания уведомления должен включать в себя вызов doSomeWorkOnNotification(), причем тоже ПОСЛЕ сохранения уведомления в базе.
После удачного вполнения этого кода в уведомлении проставляется влаг delivered = true.
В случае сбоя в коде doSomeWorkOnNotification() в уведомлении проставляется флаг delivered = false.

Написать интеграционный тест, имитирующий клиента, который ТОЛЬКО посредством АПИ совершает 1000 попыток добавления комментария в базу в максимально короткий срок.
После каждой неудачной попытки удостоверяется, что комментария в запросе списка нет, уведомление тоже отсутствует.
После каждой удачной попытки удостоверяется, что комментарий появился в списке комментариев и проверяет что уведомление также было создано (доступно по API).

После прогона теста выводится следующая статистика:
 1. Процент удачно добавленных комментариев.
 2. Процент удачно доставленных уведомлений.

Код вместе должен быть залит в открытый Git-репозиторий со всеми необходимыми файлами и инструкцией для возможности запуска.
Дополнительные очки будут присвоены тому, кто создаст и опубликует Docker-образ, позволяющий запускать данный код без предварительной подготовки окружения принимающего.
