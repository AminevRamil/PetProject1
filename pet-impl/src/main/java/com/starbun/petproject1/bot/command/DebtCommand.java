package com.starbun.petproject1.bot.command;

import com.starbun.petproject1.dto.DebtDraft;
import com.starbun.petproject1.dto.InlineButtonInfo;
import com.starbun.petproject1.dto.State;
import com.starbun.petproject1.dto.UserStateDto;
import com.starbun.petproject1.service.KeyboardCreatorService;
import com.starbun.petproject1.service.TelegramUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

@Component
public class DebtCommand extends BasicCommand {

  private final TelegramUserService telegramUserService;

  private final KeyboardCreatorService keyboardCreatorService;

  public DebtCommand(TelegramUserService telegramUserService, KeyboardCreatorService keyboardCreatorService) {
    super("debt", "Учёт долгов");
    this.telegramUserService = telegramUserService;
    this.keyboardCreatorService = keyboardCreatorService;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    UserStateDto currentStatus = telegramUserService.getCurrentStatusAndMakeTransition(user.getId(), State.DEBT_CREATE);

    SendMessage message = SendMessage.builder()
        .chatId(chat.getId())
        .text("Создать новый долг?")
        .replyMarkup(keyboardCreatorService.createInlineKeyboardForState(currentStatus.getCurrentState(), user.getId()))
        .build();
    send(absSender, message);
  }

  /**
   * Метод обработки инлайн-кнопки
   * @implNote Переделать без свитчей. Хендлеры?
   */
  public void executeInlineButton(AbsSender absSender, CallbackQuery message, InlineButtonInfo buttonData) {

    switch (buttonData.getBAction()) {
      case CREATE_DEBT -> {
        UserStateDto currentStatus = telegramUserService.getCurrentStatusAndMakeTransition(buttonData.getUId(), State.DEBT_DRAFT);
        if (currentStatus.getStateMemo() == null || !(currentStatus.getStateMemo() instanceof DebtDraft)) {
          currentStatus.setStateMemo(new DebtDraft());
        }
        DebtDraft draft = (DebtDraft) currentStatus.getStateMemo();
        EditMessageText editMessage = changeMessageWithDraftInfo(message.getMessage(), draft, buttonData.getUId());
        send(absSender, editMessage);
      }
      case CHECK_DEBTS -> {
      }
      case SELECT_DEBT -> {
      }
      case CHANGE_DEBTOR -> {
      }
      case CHANGE_DEBT_SUBJECT -> {
      }
      case CHANGE_DATE -> {
      }
      case SAVE_DEBT -> {
      }
      default -> throw new IllegalArgumentException("Обработчик не умеет обрабатывать кнопки с действием " + buttonData.getBAction());
    }
  }

  private EditMessageText changeMessageWithDraftInfo(Message originalMessage, DebtDraft draft, Long userId) {
    return EditMessageText.builder()
        .messageId(originalMessage.getMessageId())
        .chatId(originalMessage.getChatId())
        .parseMode(MARKDOWN)
        .text(renderDraftInMessage(draft))
        .replyMarkup(keyboardCreatorService.createInlineKeyboardForState(State.DEBT_DRAFT, userId))
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
