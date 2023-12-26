FROM mongo:6 as key-generator
# https://www.mongodb.com/docs/manual/tutorial/enforce-keyfile-access-control-in-existing-replica-set/#enforce-keyfile-access-control-on-existing-replica-set
RUN openssl rand -base64 756 > /mongo.key

FROM mongo:6
#https://stackoverflow.com/a/14791571/3581539
#https://stackoverflow.com/a/62754564/3581539
COPY --chmod=400 --chown=999:999 --from=key-generator /mongo.key /mongo.key
