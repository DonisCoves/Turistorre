// Generated code from Butter Knife. Do not modify!
package cf.castellon.turistorre.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GaleriaEventos$$ViewBinder<T extends cf.castellon.turistorre.fragments.GaleriaEventos> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689659, "field 'recView'");
    target.recView = finder.castView(view, 2131689659, "field 'recView'");
  }

  @Override public void unbind(T target) {
    target.recView = null;
  }
}
