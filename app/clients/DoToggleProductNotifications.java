package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import woo.core.StoreManager;

import woo.app.exception.UnknownClientKeyException;
import woo.app.exception.UnknownProductKeyException;

/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<StoreManager> {

  private Input<String> _clientKey;
  private Input<String> _productKey;

  public DoToggleProductNotifications(StoreManager storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _productKey = _form.addStringInput(Message.requestProductKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();

    String _cidCaps = _clientKey.value().toUpperCase();
    String _pidCaps = _productKey.value().toUpperCase();

    if(_receiver.isClientIDAvailable(_clientKey.value(), _cidCaps)){
      throw new UnknownClientKeyException(_clientKey.value());
    }

    if(_receiver.isProductIDAvailable(_productKey.value(), _pidCaps)){
      throw new UnknownProductKeyException(_productKey.value());
    }

    _receiver.toggleProductNotification(_cidCaps, _pidCaps);

    if(_receiver.isProductNotificationBlocked(_cidCaps, _pidCaps)){
      _display.popup(Message.notificationsOff(_clientKey.value(), _productKey.value()));
    } else {
      _display.popup(Message.notificationsOn(_clientKey.value(), _productKey.value()));
    }
  }



}
