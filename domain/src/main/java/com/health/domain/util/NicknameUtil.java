package com.health.domain.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import lombok.Getter;

public class NicknameUtil {

    private static final List<String> PREFIX_LIST =
            Arrays.stream(NicknamePrefix.values())
                    .map(NicknamePrefix::getValue)
                    .toList();

    private static final List<String> POSTFIX_LIST =
            Arrays.stream(NicknamePostfix.values())
                    .map(NicknamePostfix::getValue)
                    .toList();

    public static String createRandomNickname() {

        Random random = new Random();
        int pre = random.nextInt(19) + 1;
        int post = random.nextInt(19) + 1;
        int index = random.nextInt(99) + 1;

        return PREFIX_LIST.get(pre) + POSTFIX_LIST.get(post) + index;
    }


    @Getter
    public enum NicknamePrefix {
        NICKNAME_PREFIX_01("반짝이는"),
        NICKNAME_PREFIX_02("고요한"),
        NICKNAME_PREFIX_03("신비로운"),
        NICKNAME_PREFIX_04("따뜻한"),
        NICKNAME_PREFIX_05("용감한"),
        NICKNAME_PREFIX_06("재빠른"),
        NICKNAME_PREFIX_07("행복한"),
        NICKNAME_PREFIX_08("지혜로운"),
        NICKNAME_PREFIX_09("멋진"),
        NICKNAME_PREFIX_10("즐거운"),
        NICKNAME_PREFIX_11("조용한"),
        NICKNAME_PREFIX_12("새로운"),
        NICKNAME_PREFIX_13("빛나는"),
        NICKNAME_PREFIX_14("착한"),
        NICKNAME_PREFIX_15("강력한"),
        NICKNAME_PREFIX_16("자유로운"),
        NICKNAME_PREFIX_17("은은한"),
        NICKNAME_PREFIX_18("용맹한"),
        NICKNAME_PREFIX_19("활기찬"),
        NICKNAME_PREFIX_20("푸른");

        private final String value;

        NicknamePrefix(String value) {
            this.value = value;
        }
    }

    @Getter
    public enum NicknamePostfix {

        NICKNAME_POSTFIX_01("용사"),
        NICKNAME_POSTFIX_02("마법사"),
        NICKNAME_POSTFIX_03("여행자"),
        NICKNAME_POSTFIX_04("탐험가"),
        NICKNAME_POSTFIX_05("전사"),
        NICKNAME_POSTFIX_06("도전자"),
        NICKNAME_POSTFIX_07("예술가"),
        NICKNAME_POSTFIX_08("학자"),
        NICKNAME_POSTFIX_09("지휘자"),
        NICKNAME_POSTFIX_10("연금술사"),
        NICKNAME_POSTFIX_11("수호자"),
        NICKNAME_POSTFIX_12("기사"),
        NICKNAME_POSTFIX_13("발명가"),
        NICKNAME_POSTFIX_14("치료사"),
        NICKNAME_POSTFIX_15("무용가"),
        NICKNAME_POSTFIX_16("선생님"),
        NICKNAME_POSTFIX_17("수집가"),
        NICKNAME_POSTFIX_18("모험가"),
        NICKNAME_POSTFIX_19("연주자"),
        NICKNAME_POSTFIX_20("조각가");

        private final String value;

        NicknamePostfix(String value) {
            this.value = value;
        }
    }

}
