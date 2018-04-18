package com.bfwg.dto;

import com.ashtonhogan.obvoyage.stringvalue.StringValueOf;
import com.ashtonhogan.tacinga.asserts.ThatAssert;
import com.ashtonhogan.tacinga.conditions.NotEqual;
import com.ashtonhogan.tacinga.meta.Unit;
import com.ashtonhogan.tacinga.stringvalue.CauseString;
import com.ashtonhogan.tacinga.stringvalue.ObjectiveString;
import com.ashtonhogan.tacinga.stringvalue.PrefixString;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings({"FinalClass", "ClassWithoutLogger"})
public final class DefaultUserDetailsTest {

    @Test
    public void accountNonExpired() throws Exception {
        new ThatAssert(
                new NotEqual<>(
                        new Unit<>(
                                new ObjectiveString("Test that default user details return a non expired account status"),
                                new CauseString("An expired account status was returned"),
                                new PrefixString("Expiry Status"),
                                new DefaultUserDetails("", "", "", "", "", "", "", false, 0L, ""),
                                new ExpiredUserAccount("", "", "", "", "", "", "", false, 0L, ""),
                                UserDetails::isAccountNonExpired,
                                input -> new StringValueOf(input.getUsername())
                        )
                )
        ).evaluate();
    }
}
