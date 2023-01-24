package com.starbun.petproject1.command.debt;

import com.starbun.petproject1.command.AbstractCommand;
import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.CommandNames;
import com.starbun.petproject1.command.debt.state.DebtActions;
import com.starbun.petproject1.command.debt.state.DebtState;
import com.starbun.petproject1.command.debt.state.DebtStateMachine;
import com.starbun.petproject1.dto.ActionResponse;
import com.starbun.petproject1.dto.CommandResponse;
import com.starbun.petproject1.dto.CommandResponseType;
import com.starbun.petproject1.dto.InlineButtonInfo;
import com.starbun.petproject1.service.InlineKeyboardService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

@Slf4j
@Component(CommandNames.COMMAND_DEBT)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DebtCommand extends AbstractCommand {

  @Getter
  private final DebtStateMachine stateMachine;

  private final InlineKeyboardService<DebtState, DebtActions> debtKeyboardService;

  public DebtCommand(InlineKeyboardService<DebtState, DebtActions> debtKeyboardService,
                     List<AbstractStateProcessor<DebtState, DebtActions>> processors) {
    super(CommandNames.COMMAND_DEBT, "Учёт долгов");
    this.debtKeyboardService = debtKeyboardService;
    this.stateMachine = new DebtStateMachine(processors);
  }

  /**
   * Обработка команды/сообщения начинающегося с /debt
   */
  @Override
  public CommandResponse executeWithResponse(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    return CommandResponse.builder()
        .commandResponseType(CommandResponseType.NEW_MESSAGE)
        .messageText("Создать новый долг?")
        .chatId(chat.getId())
        .replyMarkup(debtKeyboardService.createForState(stateMachine.getCurrentState(), stateMachine.getUserOwnerId()))
        .build();
  }

  @Override
  public CommandResponse executeInlineButton(AbsSender absSender, CallbackQuery callbackQuery) {

    var info = new InlineButtonInfo(callbackQuery.getData());
    var action = DebtActions.fromCode(info.getKeyboardActionCode());

    try {
      ActionResponse actionResponse = stateMachine.performAction(action, stateMachine);

    } catch (Exception e) {
      log.error("Ошибка обработки действия инлайн кнопки: ", e);
    }
    return null;
  }
}
