// Generated code from Butter Knife. Do not modify!
package cf.castellon.turistorre.fragments.Click;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FiestasEventosRecylerView$$ViewBinder<T extends cf.castellon.turistorre.fragments.Click.FiestasEventosRecylerView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689645, "field 'recView'");
    target.recView = finder.castView(view, 2131689645, "field 'recView'");
  }

  @Override public void unbind(T target) {
    target.recView = null;
  }
}