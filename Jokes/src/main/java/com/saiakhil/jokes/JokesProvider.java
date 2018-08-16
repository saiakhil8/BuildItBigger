/*
 * Copyright (c) 2018. VENKATA SAIAKHIL KUMAR VEMULA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.saiakhil.jokes;

import java.util.Random;

public class JokesProvider {

    private final String[] jokes  = {
            "Here is joke1","Here is joke 2","Here is joke 3","No more Jokes to send..Just give a smile"
    };

    public JokesProvider(){

    }

    public String retrieveJoke(){
        return jokes[new Random().nextInt(jokes.length)] ;
    }


}
