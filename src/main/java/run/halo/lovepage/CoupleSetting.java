package run.halo.lovepage;

import java.time.LocalDate;

public record CoupleSetting(
    String left_name,
    String left_avatar,
    String left_qq,
    String left_avatar_url,
    String left_profile_url,
    String right_name,
    String right_avatar,
    String right_qq,
    String right_avatar_url,
    String right_profile_url,
    String started_at,
    String headline,
    String message,
    String cover_image
) {
    public static final String GROUP = "couple";
    public static final String DEFAULT_LEFT_AVATAR = "https://yyby.top/upload/logo.jpg";
    public static final String DEFAULT_RIGHT_AVATAR = "https://img.ffbf.top/halo/logo.png";
    private static final long DEFAULT_LOVE_DAYS = 520;

    public CoupleSetting {
        left_name = valueOrDefault(left_name, "我们");
        right_name = valueOrDefault(right_name, "彼此");
        started_at = valueOrDefault(started_at, defaultStartedAt());
        headline = valueOrDefault(headline, "我们在一起");
        message = valueOrDefault(message, "愿每一个平常的日子，都有彼此相伴。");
    }

    private static String valueOrDefault(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    /**
     * 未设置相恋日期时，以服务器当前日期回推 520 天作为默认值。
     */
    private static String defaultStartedAt() {
        return LocalDate.now().minusDays(DEFAULT_LOVE_DAYS).toString();
    }
}
