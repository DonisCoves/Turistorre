// Generated code from Butter Knife. Do not modify!
package cf.castellon.turistorre.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginFragment$$ViewBinder<T extends cf.castellon.turistorre.fragments.LoginFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689686, "field 'ivAvatar'");
    target.ivAvatar = finder.castView(view, 2131689686, "field 'ivAvatar'");
    view = finder.findRequiredView(source, 2131689690, "field 'email'");
    target.email = finder.castView(view, 2131689690, "field 'email'");
    view = finder.findRequiredView(source, 2131689691, "field 'password'");
    target.password = finder.castView(view, 2131689691, "field 'password'");
    view = finder.findRequiredView(source, 2131689693, "field 'btnNativoDesc' and method 'onClick'");
    target.btnNativoDesc = finder.castView(view, 2131689693, "field 'btnNativoDesc'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689687, "field 'layoutLoginNativo'");
    target.layoutLoginNativo = finder.castView(view, 2131689687, "field 'layoutLoginNativo'");
    view = finder.findRequiredView(source, 2131689689, "field 'layoutRegistroNativo'");
    target.layoutRegistroNativo = finder.castView(view, 2131689689, "field 'layoutRegistroNativo'");
    view = finder.findRequiredView(source, 2131689696, "field 'btnFacebook'");
    target.btnFacebook = finder.castView(view, 2131689696, "field 'btnFacebook'");
    view = finder.findRequiredView(source, 2131689695, "field 'signoutG' and method 'onClick'");
    target.signoutG = finder.castView(view, 2131689695, "field 'signoutG'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689694, "field 'mGoogleLoginButton'");
    target.mGoogleLoginButton = finder.castView(view, 2131689694, "field 'mGoogleLoginButton'");
    view = finder.findRequiredView(source, 2131689688, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689692, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.ivAvatar = null;
    target.email = null;
    target.password = null;
    target.btnNativoDesc = null;
    target.layoutLoginNativo = null;
    target.layoutRegistroNativo = null;
    target.btnFacebook = null;
    target.signoutG = null;
    target.mGoogleLoginButton = null;
  }
}
