package com.starbun.petproject1.command.debt;

import com.starbun.petproject1.command.BasicCommand;
import com.starbun.petproject1.command.CommandNames;
import com.starbun.petproject1.command.StateProcessor;
import com.starbun.petproject1.dto.DebtDraft;
import com.starbun.petproject1.dto.InlineButtonInfo;
import com.starbun.petproject1.service.TelegramUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

import static com.starbun.petproject1.command.debt.DebtCommandStates.DEBT_EDIT;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

@Slf4j
@Component(CommandNames.COMMAND_DEBT)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DebtCommand extends BasicCommand {

  // Операционные данные (состояние команды)
  private DebtStateMachine stateMachine;

  // Бины/сервисы
  private final TelegramUserService telegramUserService;
  private final DebtKeyboardService debtKeyboardService;


  public DebtCommand(TelegramUserService telegramUserService, DebtKeyboardService debtKeyboardService, List<StateProcessor<DebtKeyboardActions>> processors) {
    super(CommandNames.COMMAND_DEBT, "Учёт долгов");
    this.telegramUserService = telegramUserService;
    this.debtKeyboardService = debtKeyboardService;
    this.stateMachine = new DebtStateMachine(processors);
  }

  /**
   * Обработка команды/сообщения начинающегося с /debt
   */
  @Override
  public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    SendMessage message = SendMessage.builder()
        .chatId(chat.getId())
        .text("Создать новый долг?")
        .replyMarkup(debtKeyboardService.createForState(currentState, userOwnerId))
        .build();
    lastMessageFromBot = send(absSender, message);
  }

  /**
   * Метод обработки инлайн-кнопки
   * @implNote Переделать без свитчей. Хендлеры?
   */
  @Override
  public void executeInlineButton(AbsSender absSender, CallbackQuery callbackQuery) {

    var info = new InlineButtonInfo(callbackQuery.getData());
    var action = DebtKeyboardActions.fromCode(info.getKeyboardActionCode());

    try {
      stateMachine.performAction(action);
    } catch (Exception e) {

    }
  }

  private EditMessageText changeMessageWithDraftInfo(Message originalMessage, DebtDraft draft, Long userId) {
    return EditMessageText.builder()
        .messageId(originalMessage.getMessageId())
        .chatId(originalMessage.getChatId())
        .parseMode(MARKDOWN)
        .text(renderDraftInMessage(draft))
        .replyMarkup(debtKeyboardService.createForState(DEBT_EDIT, userId))
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
