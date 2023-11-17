# Mythinizen
Some new Customize for Denizen Script.
---
---
#### For Denizen:
    Command:
        - modelengine modelengine [set/unset] [<model_id>] [<entity>|...] (scale:<#.#>) (see_by:<entity>|...)
    Tag:
        <list.model_ids>            List All ModelEngine registed model id

---
#### For Mythicmobs:
    Targeters:
        @flagged(flag=FLAGNAME;equals="STRINGVALUE";radius="RADIUS";living=BOOLEAN;onlyplayer=BOOLEAN)
            example usage:
                - heal{a=10} @flagged(flag=test;radius=10,onlyplayer=true) ~onSpawn
    Mechanics:
        - customevent(id=CUSTOMEVENT_ID;context="ARG1:VALUE1 ARG2:VALUE2 ...")
        - denizen(command=COMMAND_TYPE;args="ENTRY_ARGS")
            example usage:
                - denizen(command=announce);args="adu vip pro")
---

