package com.key.imageio.jdk8;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.concurrent.TimeUnit;

public class DateTest {

    public static void main(String[] args) throws Exception {
//        timeUnitTest();
//        durationTest();
//        clockTest();
//        instantTest();
//        localDateTimeTest();
//        zonedDateTimeTest();
    }

    /**
     * 日期和时间转换工具
     * <p>
     * 该对象封装了丰富的时间转换，如日时分秒之间的单位换算等，就不需要我们自己封装了，直接调用
     */
    public static void timeUnitTest() {
        System.out.println(TimeUnit.HOURS.toMillis(1));
        System.out.println(TimeUnit.HOURS.toMinutes(2));
        System.out.println(TimeUnit.DAYS.toMinutes(1));
    }

    /**
     * 时间段对象（时间区间）
     * <p>
     * Duration对象是以秒为单位统计，也可转换为时，分，秒，毫秒等
     * Period对象是以年月日为单位统计，采用ISO-8601日历系统格式--PnYnMnD，如P2018Y12M31D,P12M1D
     */
    public static void durationTest() {
        // Duration对象
        Instant start = Instant.now();
        Instant end = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(3));
        Duration duration = Duration.between(start, end);

        // 得到相应的时差，按照一天等于24小时等换算
        // toDays,toHours,toMinutes,toMillis,toNanos
        System.out.println(duration.toDays());
        System.out.println(duration.toHours());
        System.out.println(duration.toMinutes());
        System.out.println(duration.toMillis());
        System.out.println(duration.toNanos());

        // 通过ofXxx手动构建时差，如天，小时，分钟，秒，毫秒，纳秒等
        // 然后通过该时差应用到某个时间的提前或推迟的操作
        // ofDays,ofHours,ofMinutes,ofSeconds,ofMillis,ofNanos
        Duration d2 = Duration.ofDays(1);
        System.out.println(d2.toDays());
        System.out.println(d2.toHours());

        // Period对象
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2).plusMonths(1);
        Period period = Period.between(startDate, endDate);
        Period period1 = Period.of(2018, 12, 31);
        System.out.println(period);
        System.out.println(period1);

    }

    /**
     * 时钟对象
     * <p>
     * 提供用于访问某个特定 时区的 瞬时时间、日期 和 时间的。
     * 这里提供的时钟是随着时间一直在变化的，即获取的均是当前的瞬时时间，可以通过Clock.fixed()来获取固定的时钟对象
     *
     * @throws InterruptedException
     */
    public static void clockTest() throws InterruptedException {
        // 创建Clock对象，默认使用UTC时钟（systemDefaultZone），
        // 可以在创建对象时设置时区，具体时区名称，参考ZoneId
        Clock defaultClock = Clock.systemDefaultZone();
        // UTC时钟（国际协调时间）
        Clock utc = Clock.systemUTC();
        // 巴黎时区
        Clock parisClock = Clock.system(ZoneId.of("Europe/Paris"));
        // 上海时区
        Clock shanghaiClock = Clock.system(ZoneId.of("Asia/Shanghai"));
        System.out.println("默认时区：" + defaultClock.millis());
        System.out.println("UTC时区：" + utc.millis());
        System.out.println("巴黎时区：" + parisClock.millis());
        System.out.println("上海时区：" + shanghaiClock.millis());

        // 创建固定瞬时时间的时钟，使用Clock.fixed(Instant,Zone)
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.of("Asia/Shanghai"));
        System.out.println("固定时间：" + fixedClock.millis());
        Thread.sleep(1000);
        System.out.println("睡眠1秒后的时间：" + fixedClock.millis());

        // 时钟的时间加减，获取某个时钟的之前或之后一定时间段的时间，使用Clock.offset()
        Clock afterClock = Clock.offset(defaultClock, Duration.ofSeconds(10));
        Clock beforeClock = Clock.offset(defaultClock, Duration.ofSeconds(-10));
        System.out.println("参考时间：" + defaultClock.millis());
        System.out.println("未来10秒的时间：" + afterClock.millis());
        System.out.println("过去10秒的时间：" + beforeClock.millis());

    }

    /**
     * 瞬时时间对象
     * <p>
     * 代表了一个确定的时间点，即相对于标准Java纪元（1970年1月1日）的偏移量，类似Date
     * 因为主要是针对秒级的操作（秒，毫秒，微秒，纳秒）
     * 作为新增时间模块中获取瞬时时间的封装，相当于System.currentTimeMillis()，不同的是，它可以精确到纳秒
     * 对于Clock等对象获取当前的时间都是调用intant.toEpochMilli()等获取
     */
    public static void instantTest() throws InterruptedException {
        // 创建Instant对象
        Instant instant = Instant.now();
        // 获取北京时间的Instant对象
        Instant beijingInstant = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
        System.out.println(instant);
        System.out.println(beijingInstant);


        // 获取当前时间相对于1970-01-01 00:00:00 UTC偏移量，分为两个字段保存的（秒和纳秒）
        System.out.println(instant.getEpochSecond());
        System.out.println(instant.getNano());
        // 获取当前时间相对于1970-01-01 00:00:00 UTC偏移量，毫秒数值（13位）
        System.out.println(instant.toEpochMilli());

        // 获取毫秒，微秒和纳秒（Instant暂时只提供获取这三个属性，年月日时分秒等未提供）
        System.out.println("毫秒：" + instant.get(ChronoField.MILLI_OF_SECOND));
        System.out.println("微秒：" + instant.get(ChronoField.MICRO_OF_SECOND));
        System.out.println("纳秒：" + instant.get(ChronoField.NANO_OF_SECOND));


        // Instant对象操作
        // 日期的延后或提前，通过instant.plus/minus + TimeUnit可以实现丰富的时间的偏移
        // plus,plusSeconds,plusMillis,plusNanos,minus,minusSeconds,minusMillis,minusNanos
        Instant plusInstant = instant.plusMillis(TimeUnit.DAYS.toMillis(1));
        Instant minusInstant = instant.minusSeconds(TimeUnit.DAYS.toSeconds(1));
        System.out.println(plusInstant);
        System.out.println(minusInstant);

        // 日期的延后或提前，通过atOffset实现，主要是针对时区差异进行的时间操作
        System.out.println(instant.atOffset(ZoneOffset.ofHours(1)));
        System.out.println(instant.atOffset(ZoneOffset.ofHoursMinutes(0, 1)));
        // 两个日期先后的判断,isAfter,isBefore
        Instant instant1 = Instant.now();
        Thread.sleep(3000);
        Instant instant2 = Instant.now();
        System.out.println(instant1.isAfter(instant2));
        System.out.println(instant1.isBefore(instant2));
        // 返回具体差值
        System.out.println(instant1.compareTo(instant2));
        System.out.println(instant2.compareTo(instant1));

    }

    /**
     * 日期与时间对象（本地时区，而不是UTC时区）
     * <p>
     * LocalDate    年-月-日
     * LocalTime    时:分:秒
     * LocalDateTime    年-月-日 T 时:分:秒
     * <p>
     * 三者的可操作的方式类似，不同的只是获取到的格式不同
     */
    public static void localDateTimeTest() {
        // 三种对象的创建都是依赖于Clock对象，因此可接收Clock对象进行生成
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        Clock clock = Clock.system(ZoneId.of("Europe/Paris"));
        LocalDateTime localDateTime1 = LocalDateTime.now(clock);
        LocalDateTime localDateTime2 = LocalDateTime.now(ZoneId.of("Europe/Paris"));
        System.out.println("LocalDate：" + localDate);
        System.out.println("LocalTime：" + localTime);
        System.out.println("LocalDateTime：" + localDateTime);
        System.out.println("LocalDateTime1：" + localDateTime1);
        System.out.println("LocalDateTime2：" + localDateTime2);

        // 也可通过ofXxx方式进行创建，直接填写年，月，日，时，分，秒，纳秒
        // time包中的月份是从1开始的，即与月份相对应，而非之前的从0开始
        LocalDateTime ll = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Paris"));
        LocalDate ld = LocalDate.of(2019, 1, 31);
        LocalTime lt = LocalTime.of(10, 10);
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        LocalDateTime ldt2 = LocalDateTime.of(2019, 2, 2, 2, 2);
        LocalDateTime ldt3 = LocalDateTime.of(2019, Month.FEBRUARY, 2, 2, 2);
        System.out.println(ll);
        System.out.println(ld);
        System.out.println(lt);
        System.out.println(ldt);
        System.out.println(ldt2);
        System.out.println(ldt3);

        // 通过parse方式创建对象
        LocalDateTime parseLDT1 = LocalDateTime.parse("2018-12-31T23:59");
        System.out.println(parseLDT1);
        LocalDateTime parseLDT2 = LocalDateTime.parse("2018-12-31T23:59:59.999");//999毫秒 等价于999000000纳秒
        System.out.println(parseLDT2);
        // 使用DateTimeFormatter解析和格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime parseLDT3 = LocalDateTime.parse("2018/12/31 23:59:59", formatter);
        // 格式化日期
        System.out.println(parseLDT3);
        System.out.println(formatter.format(parseLDT3));
        System.out.println(formatter.format(parseLDT3));
        System.out.println(parseLDT3.format(DateTimeFormatter.ISO_LOCAL_DATE));

        // 时间获取
        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getMonth());
        System.out.println(localDateTime.getDayOfYear());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getDayOfWeek());
        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getMinute());
        System.out.println(localDateTime.getSecond());
        System.out.println(localDateTime.getNano());

        // 时间修改
        System.out.println(localDate.withYear(2018));
        System.out.println(localTime.withHour(2));

        // 时间的增减，年月日时分秒均可进行增减
        LocalDateTime plusLDT = localDateTime.plusDays(1);
        LocalDateTime plusLDT2 = localDateTime.plus(1, IsoFields.QUARTER_YEARS);
        System.out.println(plusLDT);
        System.out.println(plusLDT2);
    }

    /**
     * 带有时区的日期月时间（避免与本地的date-time歧义）,存储纳秒、时区和时差
     * <p>
     * 以当前时间和世界标准时间（UTC）/格林威治时间（GMT）的偏差来计算
     * API与LocalDateTime等类似，只是多了时差(如2018-12-20T10:35:50.711+08:00[Asia/Shanghai])
     */
    public static void zonedDateTimeTest() {
        // 默认创建的对象是当前的时区
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);
        ZonedDateTime now2 = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        System.out.println(now2);
        ZonedDateTime z1 = ZonedDateTime.parse("2018-12-31T23:59:59Z[Europe/Paris]");
        System.out.println(z1);
    }

    /**
     * 日历对象
     * 与Calender类似
     * <p>
     * Java中使用的历法是ISO 8601日历系统，它是世界民用历法，也就是我们所说的公历。平年有365天，闰年是366天
     * Java 8还提供了4套其他历法,每套历法都包含一个日期类，分别是：
     * ThaiBuddhistDate：    泰国佛教历
     * MinguoDate：          中华民国历
     * JapaneseDate：        日本历
     * HijrahDate：          伊斯兰历
     * <p>
     * 在开发过程中应该尽量避免使用ChronoLocalDate，尽量用与历法无关的方式操作时间，因为不同的历法计算日期的方式不一样
     */
    public static void chronologyTest() {
        Chronology c = HijrahChronology.INSTANCE;
        ChronoLocalDateTime d = c.localDateTime(LocalDateTime.now());
        System.out.println(d);
    }
}
