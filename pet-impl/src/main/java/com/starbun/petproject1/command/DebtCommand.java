package com.starbun.petproject1.command;

import com.starbun.petproject1.dto.DebtDraft;
import com.starbun.petproject1.dto.InlineButtonInfo;
import com.starbun.petproject1.service.DebtKeyboardService;
import com.starbun.petproject1.service.TelegramUserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.starbun.petproject1.command.DebtCommand.DebtCommandStates.DEBT_DRAFT;
import static com.starbun.petproject1.command.DebtCommand.DebtCommandStates.DEBT_OPTIONS;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

@Component
@Scope("prototype")
public class DebtCommand extends BasicCommand {

  /**
   * Все состояния работы с пользователем, в которых может пребывать данная команда
   */
  @Getter
  @AllArgsConstructor
  public enum DebtCommandStates implements CommandStates {
    DEBT_OPTIONS(0, "DEBT_OPTIONS"),
    DEBT_CREATE(1, "DEBT_CREATE"),
    DEBT_DRAFT(2, "DEBT_CREATE"),
    DEBT_INPUT(3, "DEBT_INPUT"),
    DEBT_END(3, "DEBT_END");
    private final Integer id;
    private final String name;
  }

  // Операционные данные (состояние команды)
  @Getter
  private DebtCommandStates currentState = DEBT_OPTIONS;
  private DebtDraft currentEditingDebt;
  private Long userOwnerId;

  // Бины/сервисы
  private final TelegramUserService telegramUserService;
  private final DebtKeyboardService debtKeyboardService;


  public DebtCommand(TelegramUserService telegramUserService, DebtKeyboardService debtKeyboardService) {
    super("debt", "Учёт долгов");
    this.telegramUserService = telegramUserService;
    this.debtKeyboardService = debtKeyboardService;
  }

  /**
   * Метод срабатывает только при наличии "/debt" в начале текстового сообщения
   */
  @Override
  public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    currentState = DEBT_OPTIONS; //TODO Скорее всего стоит сделать свитч или какой-то механизм принятия решений

    SendMessage message = SendMessage.builder()
        .chatId(chat.getId())
        .text("Создать новый долг?")
        .replyMarkup(debtKeyboardService.createForState(currentState, userOwnerId))
        .build();
    send(absSender, message);
  }

  /**
   * Метод обработки инлайн-кнопки
   * @implNote Переделать без свитчей. Хендлеры?
   */
  @Override
  public void executeInlineButton(AbsSender absSender, CallbackQuery message, InlineButtonInfo buttonData) {

    switch (currentState) {
      case DEBT_CREATE -> {
        currentState = DEBT_DRAFT;
        if (currentEditingDebt == null) {
          currentEditingDebt = new DebtDraft();
        }
        EditMessageText editMessage = changeMessageWithDraftInfo(message.getMessage(), currentEditingDebt, buttonData.getUId());
        send(absSender, editMessage);
      }
      default -> throw new IllegalArgumentException("Обработчик не может обработать текущее состояние: " + currentState);
    }
  }

  private EditMessageText changeMessageWithDraftInfo(Message originalMessage, DebtDraft draft, Long userId) {
    return EditMessageText.builder()
        .messageId(originalMessage.getMessageId())
        .chatId(originalMessage.getChatId())
        .parseMode(MARKDOWN)
        .text(renderDraftInMessage(draft))
        .replyMarkup(debtKeyboardService.createForState(DEBT_DRAFT, userId))
        .build();
  }

  private String renderDraftInMessage(DebtDraft draft) {
    return "Текущий редактируемый долг\n" +
        "Должник: " + (draft.getDebtor() != null ? draft.getDebtor() : "Не указан") + "\n" +
        "Предмет долга: " + (draft.getDeptSubject() != null ? draft.getDeptSubject() : "Не указан") + "\n" +
        "Статус погашения: " + (draft.getRedemptionStatus() ? "Долг погашен" : "Долг не погашен") + "\n" +
        "Дата долга: " + (draft.getDebtDate() != null ? draft.getDebtDate() : "Не указана");
  }

}
