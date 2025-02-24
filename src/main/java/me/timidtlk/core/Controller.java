package me.timidtlk.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Controller {
    private boolean up;
    private boolean left;
    private boolean right;
    private boolean down;

    private boolean start;

    private boolean jump;
}
