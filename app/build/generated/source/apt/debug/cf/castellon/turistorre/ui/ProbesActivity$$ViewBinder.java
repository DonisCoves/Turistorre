// Generated code from Butter Knife. Do not modify!
package cf.castellon.turistorre.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ProbesActivity$$ViewBinder<T extends cf.castellon.turistorre.ui.ProbesActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689660, "field 'recView'");
    target.recView = finder.castView(view, 2131689660, "field 'recView'");
  }

  @Override public void unbind(T target) {
    target.recView = null;
  }
}
